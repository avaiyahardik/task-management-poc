CREATE TABLE "setup" (
    s_key           varchar(30) NOT NULL,
    s_status        boolean DEFAULT false,
    CONSTRAINT PK_SETUP PRIMARY KEY (s_key)
);

INSERT INTO "setup" (s_key, s_status) VALUES ('ONE_TIME_DATA', false);