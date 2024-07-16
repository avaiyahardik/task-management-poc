# Task Management System

### Tech Stack

- Spring Framework 3.3.1
- Java 21
- Postgres 16.3
- Gradle 8.8

### Task service root

- {{host}}/api

## Features

- JWT-based authentication
- Role-based authorization
- Sign up, Sign in and obtain new access token using refresh token
- Data validation for each API to ensure data integrity and consistency
- Generic error handling to respond with standard and consistent response structure
- Task CRUD with pagination. Get my tasks. Search tasks by specific attributes
- Archive task to a different table when deleted
- Task Discussion - Add comment, edit comment, delete comment and read comments with pagination
- Capture task change log on each task update and fetch task change log with pagination
- Database migration scripts

### Startup data generated (one time)

- 3 Admin users and 3 non-admin users will be created. Below credentials can be used to sign in with the same
    - email: `1admin@gmail.com` and password: `1admin`
    - email: `2admin@gmail.com` and password: `2admin`
    - email: `3admin@gmail.com` and password: `3admin`
    - email: `1user@gmail.com` and password: `1user`
    - email: `2user@gmail.com` and password: `2user`
    - email: `3user@gmail.com` and password: `3user`
- Created 20 tasks with random data using random user ids from above users
- Deleted 2 tasks to demonstrate task archival
- Added 10 comments to each task using random user ids from above users
- Randomly modified all tasks twice to generate task change log

## How to run Task Service

### Using Docker Compose

- Navigate the project directory on command line
- `docker-compose.yml` file is already provided with the project, need to make sure required ports are available and run
  the application using `docker-compose`
- Check if ports `5432` and `8080` are not already in use on your machine, if possible, make those ports available or
  change ports in `docker-compose.yml` file as below
- Change `- 5432:5432` for db service to `- 5433:5432` and change `- 8080:8080` for app service to `- 8081:8080` or
  whatever ports you want to use. Also change the port number under `expose` to expose port to your computer
- run `docker-compose up` to start application using docker containers

### In IntelliJ IDEA

- Install Java 21
- Start postgres 16.3 database server on local or run the same in docker container using below command. Expose different
  port if `5432` is not available for use
  `docker run --name postgres -p 5432:5432 -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -d postgres`
- Open this project in `IntelliJ IDEA`
- Create new run configuration `TaskApplication`. Set below environment variables in the run configuration
  ```APP_AUTH_REFRESH_TOKEN_SECRET=DkbHtJKhv1PNi4WYqvWc;APP_AUTH_TOKEN_SECRET=JTUlYXGntwjw5DFFEFJy;SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres;SPRING_DATASOURCE_PASSWORD=postgres;SPRING_DATASOURCE_USERNAME=postgres;SPRING_FLYWAY_URL=jdbc:postgresql://localhost:5432/postgres;SPRING_FLYWAY_PASSWORD=postgres;SPRING_FLYWAY_USER=postgres```
- Run the `TaskApplication` run configuration

### Run using command line

- Install Java 21
- Start postgres 16.3 database server on local or run the same in docker container using below command. Expose different
  port if `5432` is not available for use
  `docker run --name postgres -p 5432:5432 -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -d postgres`
- Navigate the project directory
- Export following environment variables on commandline. Change database url, username and password as needed
  ``` shell
    export APP_AUTH_TOKEN_SECRET=JTUlYXGntwjw5DFFEFJy
    export APP_AUTH_REFRESH_TOKEN_SECRET=DkbHtJKhv1PNi4WYqvWc
    export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
    export SPRING_DATASOURCE_PASSWORD=postgres
    export SPRING_DATASOURCE_USERNAME=postgres
    export SPRING_FLYWAY_URL=jdbc:postgresql://localhost:5432/postgres
    export SPRING_FLYWAY_PASSWORD=postgres
    export SPRING_FLYWAY_USER=postgres
  ```
- Run this command ```./gradlew bootRun```

## Assumptions

- FE or BE will enhance task change log history data to show appropriate reference data based on TaskFieldName for
  example user ids in change log need to be referred to respective user to get full name
