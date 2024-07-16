package com.management.task.model.taskdiscussion;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public record TaskCommentResponse(
        Long id,
        Long taskId,
        String comment,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime created,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime modified,
        Long createdById
) {
}
