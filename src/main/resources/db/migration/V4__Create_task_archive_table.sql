CREATE TABLE task_archive (
    ta_task_id       int,
    ta_title         varchar(50),
    ta_description   varchar(1024),
    ta_status        varchar(20) NOT NULL,
    ta_created_at    timestamp NOT NULL,
    ta_modified_at   timestamp NOT NULL,
    ta_created_by    int NOT NULL,
    ta_assigned_to   int,
    ta_reporter_id   int,
    ta_priority      varchar(20) NOT NULL,
    ta_due_date      date,
    CONSTRAINT PK_TASK_ARCHIVE PRIMARY KEY (ta_task_id),
    CONSTRAINT FK_TASK_ARCHIVE__CREATED_BY FOREIGN KEY (ta_created_by) REFERENCES "user"(u_id),
    CONSTRAINT FK_TASK_ARCHIVE__ASSIGNED_TO FOREIGN KEY (ta_assigned_to) REFERENCES "user"(u_id),
    CONSTRAINT FK_TASK_ARCHIVE__REPORTER_ID FOREIGN KEY (ta_reporter_id) REFERENCES "user"(u_id)
);
