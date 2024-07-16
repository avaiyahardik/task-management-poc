package com.management.task.model.taskhistory;

import java.time.OffsetDateTime;
import java.util.List;

public record TaskHistoryResponse(
        Long id,
        OffsetDateTime created,
        Long createdById,
        List<TaskHistoryLogResponse> taskHistoryLogs
) {
}
