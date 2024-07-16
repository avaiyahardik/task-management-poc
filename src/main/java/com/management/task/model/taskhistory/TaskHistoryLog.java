package com.management.task.model.taskhistory;

import com.management.task.type.TaskFieldName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task_history_log", schema = "public")
@Getter
@Setter
public class TaskHistoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thl_id")
    private Long id;

    @Column(name = "thl_task_history_id", insertable = false, updatable = false)
    private Long taskHistoryId;

    @NotNull(message = "Task field name is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "thl_field_name", length = 20)
    private TaskFieldName taskFieldName;

    @Size(max = 1024, message = "Content before {jakarta.validation.constraints.Size.message}")
    @Column(name = "thl_content_before", length = 1024)
    private String contentBefore;

    @Size(max = 1024, message = "Content after {jakarta.validation.constraints.Size.message}")
    @Column(name = "thl_content_after", length = 1024)
    private String contentAfter;

    @NotNull(message = "Task history is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thl_task_history_id")
    private TaskHistory taskHistory;
}
