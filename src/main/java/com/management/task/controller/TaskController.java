package com.management.task.controller;

import com.management.task.model.common.MessageResponse;
import com.management.task.model.task.TaskRequest;
import com.management.task.model.task.TaskResponse;
import com.management.task.model.task.TaskSearchRequest;
import com.management.task.security.CurrentUser;
import com.management.task.security.UserPrincipal;
import com.management.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing tasks.
 */
@RestController
@RequestMapping("/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Creates a new task.
     *
     * @param taskRequest   The request body containing task details.
     * @param userPrincipal The authenticated user principal.
     * @return A {@link TaskResponse} containing the created task details.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public TaskResponse createTask(@Valid @RequestBody TaskRequest taskRequest, @CurrentUser UserPrincipal userPrincipal) {
        return taskService.createTask(userPrincipal.getId(), taskRequest);
    }

    /**
     * Updates an existing task.
     *
     * @param taskId        The ID of the task to update.
     * @param taskRequest   The request body containing updated task details.
     * @param userPrincipal The authenticated user principal.
     * @return A {@link TaskResponse} containing the updated task details.
     */
    @PutMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public TaskResponse updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest, @CurrentUser UserPrincipal userPrincipal) {
        return taskService.updateTask(taskId, userPrincipal.getId(), taskRequest);
    }

    /**
     * Searches for tasks based on the provided search criteria.
     *
     * @param taskSearchRequest The search criteria for tasks.
     * @param pageable          The pagination and sorting information.
     * @return A {@link Page} of {@link TaskResponse} containing the search results.
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public Page<TaskResponse> searchTasks(@Valid TaskSearchRequest taskSearchRequest, Pageable pageable) {
        return taskService.searchTasks(taskSearchRequest, pageable);
    }

    /**
     * Retrieves tasks assigned to the authenticated user.
     *
     * @param pageable      The pagination and sorting information.
     * @param userPrincipal The authenticated user principal.
     * @return A {@link Page} of {@link TaskResponse} containing the tasks assigned to the user.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public Page<TaskResponse> getMyTasks(@PageableDefault(sort = "dueDate", direction = Sort.Direction.DESC) Pageable pageable, @CurrentUser UserPrincipal userPrincipal) {
        return taskService.getMyTasks(userPrincipal.getId(), pageable);
    }

    /**
     * Retrieves details of a specific task.
     *
     * @param taskId        The ID of the task to retrieve.
     * @param userPrincipal The authenticated user principal.
     * @return A {@link TaskResponse} containing the details of the requested task.
     */
    @GetMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public TaskResponse getTask(@PathVariable Long taskId, @CurrentUser UserPrincipal userPrincipal) {
        return taskService.getTask(taskId, userPrincipal);
    }

    /**
     * Deletes a task with the specified ID.
     *
     * @param taskId The ID of the task to delete.
     * @return A {@link MessageResponse} indicating the result of the deletion operation.
     */
    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new MessageResponse(String.format("Task %d deleted successfully", taskId));
    }
}
