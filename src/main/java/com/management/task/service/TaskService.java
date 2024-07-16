package com.management.task.service;

import com.management.task.exception.AccessDeniedException;
import com.management.task.exception.ResourceNotFoundException;
import com.management.task.mapper.TaskMapper;
import com.management.task.model.task.Task;
import com.management.task.model.task.TaskRequest;
import com.management.task.model.task.TaskResponse;
import com.management.task.model.task.TaskSearchRequest;
import com.management.task.repository.TaskRepository;
import com.management.task.security.UserPrincipal;
import com.management.task.type.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing tasks.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskArchiveService taskArchiveService;
    private final TaskHistoryService taskHistoryService;
    private final UserService userService;

    /**
     * Retrieves a task by its ID from the database.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task object if found.
     * @throws ResourceNotFoundException If no task exists with the given ID.
     */
    private Task findById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
    }

    public boolean existsById(Long taskId) {
        return taskRepository.existsById(taskId);
    }

    /**
     * Creates a new task based on the provided task request.
     *
     * @param currentUserId The ID of the current user creating the task.
     * @param taskRequest   The request containing details of the task to create.
     * @return A TaskResponse object representing the created task.
     */
    public TaskResponse createTask(Long currentUserId, TaskRequest taskRequest) {
        log.info("Create task request from user [{}]: {}", currentUserId, taskRequest);
        validateUserExists(taskRequest.assignedToId());
        validateUserExists(taskRequest.reporterId());
        return taskMapper.mapForResponse(taskRepository.save(taskMapper.mapCreateRequest(taskRequest, currentUserId)));
    }

    /**
     * Updates an existing task identified by its ID.
     *
     * @param taskId        The ID of the task to update.
     * @param currentUserId The ID of the current user updating the task.
     * @param taskRequest   The request containing updated details of the task.
     * @return A TaskResponse object representing the updated task.
     */
    @Transactional
    public TaskResponse updateTask(Long taskId, Long currentUserId, TaskRequest taskRequest) {
        log.info("Update task [{}] request from user [{}]: {}", taskId, currentUserId, taskRequest);
        validateUserExists(taskRequest.assignedToId());
        validateUserExists(taskRequest.reporterId());
        Task task = findById(taskId);
        taskHistoryService.logChangeHistory(task, taskRequest, currentUserId);
        taskMapper.update(task, taskRequest);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.mapForResponse(updatedTask);
    }

    /**
     * Searches for tasks based on the provided search criteria.
     *
     * @param taskSearchRequest The criteria to search tasks.
     * @param pageable          The pagination and sorting information.
     * @return A Page of TaskResponse objects representing the search results.
     */
    @Transactional(readOnly = true)
    public Page<TaskResponse> searchTasks(TaskSearchRequest taskSearchRequest, Pageable pageable) {
        return taskRepository.queryPage(taskSearchRequest, pageable).map(taskMapper::mapForResponse);
    }

    /**
     * Retrieves tasks assigned to the current user.
     *
     * @param currentUserId The ID of the current user.
     * @param pageable      The pagination and sorting information.
     * @return A Page of TaskResponse objects representing tasks assigned to the current user.
     */
    public Page<TaskResponse> getMyTasks(Long currentUserId, Pageable pageable) {
        return taskRepository.findByAssignedToId(currentUserId, pageable).map(taskMapper::mapForResponse);
    }

    /**
     * Retrieves details of a specific task identified by its ID, validating user permissions.
     *
     * @param taskId        The ID of the task to retrieve.
     * @param userPrincipal The authenticated user principal.
     * @return A TaskResponse object representing the requested task.
     * @throws AccessDeniedException If the authenticated user does not have permission to access the task.
     */
    public TaskResponse getTask(Long taskId, UserPrincipal userPrincipal) {
        Task task = findById(taskId);
        validateTaskPermission(task, userPrincipal);
        return taskMapper.mapForResponse(task);
    }

    /**
     * Validates if the authenticated user has permission to access the task.
     *
     * @param task          The task object to check permission against.
     * @param userPrincipal The authenticated user principal.
     * @throws AccessDeniedException If the authenticated user does not have permission to access the task.
     */
    private void validateTaskPermission(Task task, UserPrincipal userPrincipal) {
        Long currentUserId = userPrincipal.getId();
        if (userPrincipal.getRoles().contains(Role.ROLE_ADMIN.name())) {
            return;
        }
        if (currentUserId.equals(task.getCreatedById()) || currentUserId.equals(task.getAssignedToId()) || currentUserId.equals(task.getReporterId())) {
            return;
        }
        throw new AccessDeniedException();
    }

    private void validateUserExists(Long userId) {
        if (userId != null && !userService.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    /**
     * Deletes a task identified by its ID. And archive the same.
     *
     * @param taskId The ID of the task to delete.
     */
    @Transactional
    public void deleteTask(Long taskId) {
        log.info("Delete task request for task [{}]", taskId);
        Task task = findById(taskId);
        taskRepository.delete(task);
        taskArchiveService.archiveTask(task);
        log.info("Deleted task [{}]", taskId);
    }
}
