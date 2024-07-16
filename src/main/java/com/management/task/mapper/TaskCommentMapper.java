package com.management.task.mapper;

import com.management.task.model.taskdiscussion.TaskComment;
import com.management.task.model.taskdiscussion.TaskCommentRequest;
import com.management.task.model.taskdiscussion.TaskCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskCommentMapper {

    @Mapping(target = "createdById", source = "createdById")
    @Mapping(target = "taskId", source = "taskId")
    TaskComment mapCreateRequest(Long taskId, TaskCommentRequest taskCommentRequest, Long createdById);

    @Mapping(target = "modified", expression = "java(OffsetDateTime.now())")
    void update(@MappingTarget TaskComment taskComment, TaskCommentRequest taskCommentRequest);

    TaskCommentResponse mapForResponse(TaskComment taskComment);
}
