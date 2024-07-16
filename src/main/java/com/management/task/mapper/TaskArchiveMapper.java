package com.management.task.mapper;

import com.management.task.model.task.Task;
import com.management.task.model.task.TaskArchive;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskArchiveMapper {
    @Mapping(target = "taskId", source = "task.id")
    TaskArchive map(Task task);
}
