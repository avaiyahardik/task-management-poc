package com.management.task.model.taskhistory;

import com.management.task.model.task.Task;
import com.management.task.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_history", schema = "public")
@Getter
@Setter
public class TaskHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "th_id")
    private Long id;

    @NotNull(message = "Task id is required")
    @Column(name = "th_task_id")
    private Long taskId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "th_created_at", updatable = false)
    private OffsetDateTime created;

    @NotNull(message = "Created by is required")
    @Column(name = "th_created_by", updatable = false)
    private Long createdById;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "th_created_by", insertable = false, updatable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "th_task_id", insertable = false, updatable = false)
    private Task task;

    @OneToMany(mappedBy = "taskHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskHistoryLog> taskHistoryLogs = new ArrayList<>();

    public void addTaskHistoryLog(TaskHistoryLog taskHistoryLog) {
        taskHistoryLog.setTaskHistory(this);
        this.taskHistoryLogs.add(taskHistoryLog);
    }

}
