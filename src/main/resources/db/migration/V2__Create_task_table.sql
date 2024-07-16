CREATE TABLE task (
    t_id            serial,
    t_title         varchar(50),
    t_description   varchar(1024),
    t_status        varchar(20) NOT NULL DEFAULT 'TODO',
    t_created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    t_modified_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    t_created_by    int NOT NULL,
    t_assigned_to   int,
    t_reporter_id   int,
    t_priority      varchar(20) NOT NULL,
    t_due_date      date,
    CONSTRAINT PK_TASK PRIMARY KEY (t_id),
    CONSTRAINT FK_TASK__CREATED_BY FOREIGN KEY (t_created_by) REFERENCES "user"(u_id),
    CONSTRAINT FK_TASK__ASSIGNED_TO FOREIGN KEY (t_assigned_to) REFERENCES "user"(u_id),
    CONSTRAINT FK_TASK__REPORTER_ID FOREIGN KEY (t_reporter_id) REFERENCES "user"(u_id)
);
