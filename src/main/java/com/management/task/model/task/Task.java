package com.management.task.model.task;

import com.management.task.listener.TaskListener;
import com.management.task.model.taskdiscussion.TaskComment;
import com.management.task.model.taskhistory.TaskHistory;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task", schema = "public")
@Getter
@Setter
@EntityListeners(TaskListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "t_created_at", updatable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "t_modified_at")
    private OffsetDateTime modified = OffsetDateTime.now();

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title {jakarta.validation.constraints.Size.message}")
    @Column(name = "t_title", length = 50)
    private String title;

    @Size(max = 1024, message = "Description {jakarta.validation.constraints.Size.message}")
    @Column(name = "t_description", length = 1024)
    private String description;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "t_status", length = 20)
    private TaskStatus status = TaskStatus.TODO;

    @NotNull(message = "Created by is required")
    @Column(name = "t_created_by", updatable = false)
    private Long createdById;

    @Column(name = "t_assigned_to")
    private Long assignedToId;

    @Column(name = "t_reporter_id")
    private Long reporterId;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "t_priority", length = 20)
    private TaskPriority priority;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "t_due_date")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_created_by", insertable = false, updatable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_assigned_to", insertable = false, updatable = false)
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_reporter_id", insertable = false, updatable = false)
    private User reporter;

    // Ideally we should archive comments like tasks. deleting currently for convenience
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskComment> taskComments = new ArrayList<>();

    // Ideally we should archive comments like tasks. deleting currently for convenience
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistory> taskHistories = new ArrayList<>();
}
