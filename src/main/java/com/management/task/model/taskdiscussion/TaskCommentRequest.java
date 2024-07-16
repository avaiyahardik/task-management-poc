package com.management.task.model.taskdiscussion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCommentRequest(
        @NotBlank(message = "Comment is required")
        @Size(max = 1024, message = "Comment {jakarta.validation.constraints.Size.message}")
        String comment
) {
}
