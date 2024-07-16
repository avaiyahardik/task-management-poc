package com.management.task.setup;

import com.management.task.model.task.Task;
import com.management.task.model.task.TaskRequest;
import com.management.task.model.taskdiscussion.TaskCommentRequest;
import com.management.task.model.taskhistory.Setup;
import com.management.task.model.user.User;
import com.management.task.repository.SetupRepository;
import com.management.task.repository.TaskRepository;
import com.management.task.repository.UserRepository;
import com.management.task.security.model.SignUpRequest;
import com.management.task.service.TaskCommentService;
import com.management.task.service.TaskService;
import com.management.task.service.UserService;
import com.management.task.type.Role;
import com.management.task.type.StartupSetup;
import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleDataGenerator implements CommandLineRunner {

    private final SetupRepository setupRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final TaskService taskService;
    private final TaskCommentService taskCommentService;

    private final Faker dataFaker = new Faker();
    private final Random random = new Random();

    private static final String ADMIN_USER_EMAIL = "admin@gmail.com";
    private static final String ADMIN_USER_PASSWORD = "admin";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final String USER_PASSWORD = "user";

    private static final String ONE_TIME_DATA_SETUP_KEY = StartupSetup.ONE_TIME_DATA.name();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Optional<Setup> optionalSetup = setupRepository.findById(ONE_TIME_DATA_SETUP_KEY);
        Setup setup;
        if (optionalSetup.isPresent()) {
            setup = optionalSetup.get();
            if (setup.isStatus()) {
                log.info("One time data setup is already completed hence won't setup again");
                return;
            } else {
                setup.setStatus(true);
            }
        } else {
            setup = new Setup();
            setup.setId(ONE_TIME_DATA_SETUP_KEY);
            setup.setStatus(true);
        }
        setupOneTimeData();
        setupRepository.save(setup);
        log.info("[SETUP] One time startup data setup is completed!");
    }

    private void setupOneTimeData() {
        signupUsers();
        setupTasks();
        deleteTasks();
        setupTaskComments();
        modifyTasks();
        modifyTasks();  // modify tasks again to generate more change log
    }

    // Create 3 Admin users and 3 non-admin users
    private void signupUsers() {
        for (int i = 1; i <= 3; i++) {
            String adminEmail = i + ADMIN_USER_EMAIL;
            String adminPassword = i + ADMIN_USER_PASSWORD;
            SignUpRequest adminSignupRequest = new SignUpRequest(dataFaker.name().firstName(), dataFaker.name().lastName(), adminEmail, adminPassword, Role.ROLE_ADMIN);
            userService.createUser(adminSignupRequest);
            log.info("[SETUP] Admin user email: {} and password: {}", adminEmail, adminPassword);

            String userEmail = i + USER_EMAIL;
            String userPassword = i + USER_PASSWORD;
            SignUpRequest userSignupRequest = new SignUpRequest(dataFaker.name().firstName(), dataFaker.name().lastName(), userEmail, userPassword, Role.ROLE_USER);
            userService.createUser(userSignupRequest);
            log.info("[SETUP] Simple user email: {} and password: {}", userEmail, userPassword);
        }
    }

    private TaskStatus randomTaskStatus() {
        TaskStatus[] taskStatuses = TaskStatus.values();
        return taskStatuses[random.nextInt(taskStatuses.length)];
    }

    private TaskPriority randomTaskPriority() {
        TaskPriority[] taskPriorities = TaskPriority.values();
        return taskPriorities[random.nextInt(taskPriorities.length)];
    }

    private long randomUserId() {
        Long[] userIds = userRepository.findAll().stream().map(User::getId).toList().toArray(new Long[0]);
        ;
        return userIds[random.nextInt(userIds.length)];
    }

    // get due date between today and nex 7 days
    private LocalDate randomDueDate() {
        return LocalDate.now().plusDays(random.nextInt(7));
    }

    // Create 20 tasks
    private void setupTasks() {
        TaskRequest taskRequest;
        for (int i = 1; i <= 20; i++) {
            taskRequest = new TaskRequest(StringUtils.left(dataFaker.book().title(), 50), StringUtils.left(dataFaker.superhero().descriptor(), 1024),
                    randomTaskStatus(), randomUserId(), randomUserId(), randomTaskPriority(), randomDueDate());
            taskService.createTask(randomUserId(), taskRequest);
        }
    }

    // Create 10 comments on each task
    private void setupTaskComments() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            TaskCommentRequest taskCommentRequest;
            for (int i = 1; i <= 10; i++) {
                taskCommentRequest = new TaskCommentRequest(StringUtils.left(dataFaker.weather().description(), 1024));
                taskCommentService.createTaskComment(task.getId(), randomUserId(), taskCommentRequest);
            }
        }
    }

    // Modify tasks randomly to generate task change log
    private void modifyTasks() {
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            TaskRequest taskRequest = new TaskRequest(StringUtils.left(dataFaker.book().title(), 50), StringUtils.left(dataFaker.weather().description(), 1024),
                    randomTaskStatus(), randomUserId(), randomUserId(), randomTaskPriority(), randomDueDate());
            taskService.updateTask(task.getId(), task.getCreatedById(), taskRequest);
        }
    }

    // Delete two tasks to archive tasks
    private void deleteTasks() {
        List<Task> tasks = taskRepository.findAll().subList(5, 7);
        for (Task task : tasks) {
            taskService.deleteTask(task.getId());
        }
    }
}