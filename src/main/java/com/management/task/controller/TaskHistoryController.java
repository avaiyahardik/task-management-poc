package com.management.task.controller;

import com.management.task.model.taskhistory.TaskHistoryResponse;
import com.management.task.service.TaskHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the history of tasks.
 * Provides an endpoint for retrieving a paginated list of task histories.
 */
@RestController
@RequestMapping("/v1/task/{taskId}/history")
@RequiredArgsConstructor
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    /**
     * Retrieves a paginated list of history entries for a specific task.
     *
     * @param taskId   the ID of the task for which histories are to be retrieved.
     * @param pageable pagination information including page number, size, and sorting.
     * @return a paginated list of task history entries for the specified task.
     */
    @GetMapping
    public Page<TaskHistoryResponse> getTaskHistories(@PathVariable Long taskId, Pageable pageable) {
        return taskHistoryService.getTaskHistories(taskId, pageable);
    }

}
