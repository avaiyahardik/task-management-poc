package com.management.task.model.task;

import com.management.task.model.user.User;
import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "task_archive", schema = "public")
@Getter
@Setter
public class TaskArchive {

    @Id
    @Column(name = "ta_task_id")
    private Long taskId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "ta_created_at", updatable = false)
    private OffsetDateTime created;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "ta_modified_at")
    private OffsetDateTime modified;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title {jakarta.validation.constraints.Size.message}")
    @Column(name = "ta_title", length = 50)
    private String title;

    @Size(max = 1024, message = "Description {jakarta.validation.constraints.Size.message}")
    @Column(name = "ta_description", length = 1024)
    private String description;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "ta_status", length = 20)
    private TaskStatus status;

    @NotNull(message = "Created by is required")
    @Column(name = "ta_created_by")
    private Long createdById;

    @Column(name = "ta_assigned_to")
    private Long assignedToId;

    @Column(name = "ta_reporter_id")
    private Long reporterId;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "ta_priority", length = 20)
    private TaskPriority priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "ta_due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_created_by", insertable = false, updatable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_assigned_to", insertable = false, updatable = false)
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ta_reporter_id", insertable = false, updatable = false)
    private User reporter;
}
