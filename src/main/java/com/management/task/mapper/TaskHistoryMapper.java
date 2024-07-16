package com.management.task.mapper;

import com.management.task.model.taskhistory.TaskHistory;
import com.management.task.model.taskhistory.TaskHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskHistoryMapper {
    TaskHistoryResponse mapForResponse(TaskHistory taskHistory);
}
