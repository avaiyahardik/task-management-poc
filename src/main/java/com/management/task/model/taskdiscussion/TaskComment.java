package com.management.task.model.taskdiscussion;

import com.management.task.model.task.Task;
import com.management.task.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Entity
@Table(name = "task_comment", schema = "public")
@Getter
@Setter
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tc_id")
    private Long id;

    @NotNull(message = "Task id is required")
    @Column(name = "tc_task_id")
    private Long taskId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "tc_created_at", updatable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "tc_modified_at")
    private OffsetDateTime modified = OffsetDateTime.now();

    @NotBlank(message = "Comment is required")
    @Size(max = 1024, message = "Comment {jakarta.validation.constraints.Size.message}")
    @Column(name = "tc_comment", length = 1024)
    private String comment;

    @NotNull(message = "Created by is required")
    @Column(name = "tc_created_by", updatable = false)
    private Long createdById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tc_created_by", insertable = false, updatable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tc_task_id", insertable = false, updatable = false)
    private Task task;
}
