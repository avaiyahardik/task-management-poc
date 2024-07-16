package com.management.task.listener;

import com.management.task.model.task.Task;
import com.management.task.service.TaskArchiveService;
import jakarta.persistence.PostRemove;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Component that listens to task lifecycle events.
 * Specifically, it handles the event when a task entity is removed from the persistence context.
 */
@Component
public class TaskListener {

    private final TaskArchiveService taskArchiveService;

    /**
     * Constructs a {@link TaskListener} with the given {@link TaskArchiveService}.
     *
     * @param taskArchiveService the service used to archive tasks.
     */
    public TaskListener(@Lazy TaskArchiveService taskArchiveService) {
        this.taskArchiveService = taskArchiveService;
    }

    /**
     * Handles the event when a task entity is removed from the persistence context.
     * This method is called after a task is deleted from the database.
     * It delegates the task archiving to the {@link TaskArchiveService}.
     *
     * @param task the {@link Task} entity that was removed.
     */
    @PostRemove
    public void onPostRemove(Task task) {
        taskArchiveService.archiveTask(task);
    }

}
