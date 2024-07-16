package com.management.task.model.task;

import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record TaskResponse(
        Long id,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime created,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime modified,
        String title,
        String description,
        TaskStatus status,
        Long createdById,
        Long assignedToId,
        Long reporterId,
        TaskPriority priority,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate dueDate
) {
}
