package com.management.task.mapper;

import com.management.task.model.task.Task;
import com.management.task.model.task.TaskRequest;
import com.management.task.model.task.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.OffsetDateTime;

/**
 * Mapper interface for mapping between Task, TaskRequest, and TaskResponse objects.
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {OffsetDateTime.class})
public interface TaskMapper {
    /**
     * Maps a TaskRequest and a creator ID to a Task object.
     *
     * @param taskRequest The TaskRequest containing details of the task.
     * @param createdById The ID of the user who created the task.
     * @return A Task object mapped from TaskRequest.
     */
    @Mapping(target = "createdById", source = "createdById")
    Task mapCreateRequest(TaskRequest taskRequest, Long createdById);

    /**
     * Updates an existing Task object with details from TaskRequest.
     *
     * @param task        The Task object to update.
     * @param taskRequest The TaskRequest containing updated details.
     */
    @Mapping(target = "modified", expression = "java(OffsetDateTime.now())")
    void update(@MappingTarget Task task, TaskRequest taskRequest);

    /**
     * Maps a Task object to a TaskResponse object.
     *
     * @param task The Task object to map.
     * @return A TaskResponse object mapped from Task.
     */
    TaskResponse mapForResponse(Task task);

}
