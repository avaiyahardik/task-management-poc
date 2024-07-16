package com.management.task.service;

import com.management.task.mapper.TaskArchiveMapper;
import com.management.task.model.task.Task;
import com.management.task.model.task.TaskArchive;
import com.management.task.repository.TaskArchiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for archiving tasks.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskArchiveService {
    private final TaskArchiveRepository taskArchiveRepository;
    private final TaskArchiveMapper taskArchiveMapper;

    /**
     * Archives the given task by mapping it to a TaskArchive entity and saving it to the database.
     *
     * @param task The task to archive.
     */
    public void archiveTask(Task task) {
        TaskArchive taskArchive = taskArchiveMapper.map(task);
        taskArchiveRepository.save(taskArchive);
        log.info("Archived task {}", task.getId());
    }
}
