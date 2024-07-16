package com.management.task.model.taskhistory;

import com.management.task.type.TaskFieldName;

public record TaskHistoryLogResponse(
        Long id,
        TaskFieldName taskFieldName,
        String contentBefore,
        String contentAfter
) {
}
