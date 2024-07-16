CREATE TABLE task_history (
    th_id           serial,
    th_task_id      int NOT NULL,
    th_created_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    th_created_by   int not null,
    CONSTRAINT PK_TASK_HISTORY PRIMARY KEY (th_id),
    CONSTRAINT FK_TASK_COMMENT__TASK_ID FOREIGN KEY (th_task_id) REFERENCES "task"(t_id),
    CONSTRAINT FK_TASK_HISTORY__CREATED_BY FOREIGN KEY (th_created_by) REFERENCES "user"(u_id)
);

CREATE TABLE task_history_log (
    thl_id                  serial,
    thl_task_history_id     int not null,
    thl_field_name          varchar(20),
    thl_content_before      text,
    thl_content_after       text,
    CONSTRAINT PK_TASK_HISTORY_LOG PRIMARY KEY (thl_id),
    CONSTRAINT FK_TASK_HISTORY_LOG__TASK_HISTORY_ID FOREIGN KEY (thl_task_history_id) REFERENCES task_history(th_id)
);
