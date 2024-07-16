CREATE TABLE "user" (
    u_id            serial,
    u_firstname     varchar(30) NOT NULL,
    u_lastname      varchar(30),
    u_email         varchar(128) NOT NULL,
    u_password      varchar(128) NOT NULL,
    u_role          varchar(20),
    u_created_at    timestamp DEFAULT CURRENT_TIMESTAMP,
    u_modified_at   timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_USER PRIMARY KEY (u_id),
    CONSTRAINT UK_USER__EMAIL UNIQUE (u_email)
);
