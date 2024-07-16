CREATE TABLE task_comment (
    tc_id            serial,
    tc_task_id       int NOT NULL,
    tc_comment       varchar(1024) NOT NULL,
    tc_created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    tc_modified_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    tc_created_by    int NOT NULL,
    CONSTRAINT PK_TASK_COMMENT PRIMARY KEY (tc_id),
    CONSTRAINT FK_TASK_COMMENT__CREATED_BY FOREIGN KEY (tc_created_by) REFERENCES "user"(u_id),
    CONSTRAINT FK_TASK_COMMENT__TASK_ID FOREIGN KEY (tc_task_id) REFERENCES "task"(t_id)
);
