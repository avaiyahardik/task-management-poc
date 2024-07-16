package com.management.task.controller;

import com.management.task.model.common.MessageResponse;
import com.management.task.model.taskdiscussion.TaskCommentRequest;
import com.management.task.model.taskdiscussion.TaskCommentResponse;
import com.management.task.security.CurrentUser;
import com.management.task.security.UserPrincipal;
import com.management.task.service.TaskCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing comments on tasks.
 * Provides endpoints for creating, updating, retrieving, and deleting task comments.
 */
@RestController
@RequestMapping("/v1/task/{taskId}/comment")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    /**
     * Creates a new comment for a specific task.
     *
     * @param taskId             the ID of the task to which the comment will be added.
     * @param taskCommentRequest the request body containing the details of the comment to be created.
     * @param userPrincipal      the currently authenticated user making the request.
     * @return the response containing the details of the created comment.
     */
    @PostMapping
    public TaskCommentResponse createTaskComment(@PathVariable Long taskId, @Valid @RequestBody TaskCommentRequest taskCommentRequest, @CurrentUser UserPrincipal userPrincipal) {
        return taskCommentService.createTaskComment(taskId, userPrincipal.getId(), taskCommentRequest);
    }

    /**
     * Updates an existing comment on a specific task.
     *
     * @param taskId             the ID of the task to which the comment belongs.
     * @param taskCommentId      the ID of the comment to be updated.
     * @param taskCommentRequest the request body containing the updated details of the comment.
     * @param userPrincipal      the currently authenticated user making the request.
     * @return the response containing the details of the updated comment.
     */
    @PutMapping("/{taskCommentId}")
    public TaskCommentResponse updateTask(@PathVariable Long taskId, @PathVariable Long taskCommentId, @Valid @RequestBody TaskCommentRequest taskCommentRequest, @CurrentUser UserPrincipal userPrincipal) {
        return taskCommentService.updateTaskComment(taskId, taskCommentId, userPrincipal.getId(), taskCommentRequest);
    }

    /**
     * Retrieves a paginated list of comments for a specific task.
     *
     * @param taskId   the ID of the task for which comments are to be retrieved.
     * @param pageable pagination information including page number, size, and sorting.
     * @return a paginated list of comments for the specified task.
     */
    @GetMapping
    public Page<TaskCommentResponse> getTaskComments(@PathVariable Long taskId, Pageable pageable) {
        return taskCommentService.getTaskComments(taskId, pageable);
    }

    /**
     * Deletes a specific comment from a task.
     *
     * @param taskId        the ID of the task from which the comment will be deleted.
     * @param taskCommentId the ID of the comment to be deleted.
     * @param userPrincipal the currently authenticated user making the request.
     * @return a response message indicating the result of the delete operation.
     */
    @DeleteMapping("/{taskCommentId}")
    public MessageResponse deleteTaskComment(@PathVariable Long taskId, @PathVariable Long taskCommentId, @CurrentUser UserPrincipal userPrincipal) {
        taskCommentService.deleteTaskComment(taskId, taskCommentId, userPrincipal.getId());
        return new MessageResponse(String.format("Task Comment %d deleted successfully", taskCommentId));
    }
}
