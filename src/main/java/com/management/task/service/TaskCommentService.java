package com.management.task.service;

import com.management.task.exception.AccessDeniedException;
import com.management.task.exception.BadRequestException;
import com.management.task.exception.ResourceNotFoundException;
import com.management.task.mapper.TaskCommentMapper;
import com.management.task.model.taskdiscussion.TaskComment;
import com.management.task.model.taskdiscussion.TaskCommentRequest;
import com.management.task.model.taskdiscussion.TaskCommentResponse;
import com.management.task.repository.TaskCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCommentService {
    private final TaskCommentMapper taskCommentMapper;
    private final TaskCommentRepository taskCommentRepository;
    private final TaskService taskService;

    private TaskComment findById(Long taskCommentId) {
        return taskCommentRepository.findById(taskCommentId).orElseThrow(() -> new ResourceNotFoundException("TaskComment", "id", taskCommentId));
    }

    public TaskCommentResponse createTaskComment(Long taskId, Long currentUserId, TaskCommentRequest taskCommentRequest) {
        log.info("Create task comment for task [{}] request from user [{}]: {}", taskId, currentUserId, taskCommentRequest);
        if (!taskService.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        TaskComment taskComment = taskCommentMapper.mapCreateRequest(taskId, taskCommentRequest, currentUserId);
        return taskCommentMapper.mapForResponse(taskCommentRepository.save(taskComment));
    }

    public Page<TaskCommentResponse> getTaskComments(Long taskId, Pageable pageable) {
        return taskCommentRepository.findByTaskIdOrderByCreatedDesc(taskId, pageable).map(taskCommentMapper::mapForResponse);
    }

    public void deleteTaskComment(Long taskId, Long taskCommentId, Long currentUserId) {
        log.info("Delete task comment request for task comment [{}:{}]", taskId, taskCommentId);
        if (!taskService.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        TaskComment taskComment = findById(taskCommentId);
        validateCommentTask(taskComment, taskId);
        validateCommentOwner(taskComment, currentUserId);
        taskCommentRepository.delete(taskComment);
    }

    public TaskCommentResponse updateTaskComment(Long taskId, Long taskCommentId, Long currentUserId, TaskCommentRequest taskCommentRequest) {
        log.info("Update task comment [{}::{}] request from user [{}]: {}", taskId, taskCommentId, currentUserId, taskCommentRequest);
        if (!taskService.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        TaskComment taskComment = findById(taskCommentId);
        validateCommentTask(taskComment, taskId);
        validateCommentOwner(taskComment, currentUserId);
        taskCommentMapper.update(taskComment, taskCommentRequest);
        return taskCommentMapper.mapForResponse(taskCommentRepository.save(taskComment));
    }

    private void validateCommentTask(TaskComment taskComment, Long taskId) {
        if (!taskComment.getTaskId().equals(taskId)) {
            throw new BadRequestException("Task id mismatch");
        }
    }

    private void validateCommentOwner(TaskComment taskComment, Long userId) {
        if (!taskComment.getCreatedById().equals(userId)) {
            throw new AccessDeniedException("Unauthorized action for the task comment");
        }
    }
}
