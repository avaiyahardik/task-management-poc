package com.management.task.model.task;

import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 50, message = "Title {jakarta.validation.constraints.Size.message}")
        String title,
        @Size(max = 1024, message = "Description {jakarta.validation.constraints.Size.message}")
        String description,
        @NotNull(message = "Status is required")
        TaskStatus status,
        Long assignedToId,
        Long reporterId,
        @NotNull(message = "Priority is required")
        TaskPriority priority,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dueDate
) {
}
