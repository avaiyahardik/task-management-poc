package com.management.task.service;

import com.management.task.mapper.TaskHistoryMapper;
import com.management.task.model.task.Task;
import com.management.task.model.task.TaskRequest;
import com.management.task.model.taskhistory.TaskHistory;
import com.management.task.model.taskhistory.TaskHistoryLog;
import com.management.task.model.taskhistory.TaskHistoryResponse;
import com.management.task.repository.TaskHistoryRepository;
import com.management.task.type.TaskFieldName;
import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private final TaskHistoryMapper taskHistoryMapper;
    private final TaskHistoryRepository taskHistoryRepository;

    @Transactional(readOnly = true)
    public Page<TaskHistoryResponse> getTaskHistories(Long taskId, Pageable pageable) {
        return taskHistoryRepository.findByTaskIdOrderByCreatedDesc(taskId, pageable).map(taskHistoryMapper::mapForResponse);
    }

    public void logChangeHistory(Task currentTask, TaskRequest updateTaskRequest, Long currentUserId) {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskId(currentTask.getId());
        taskHistory.setTask(currentTask);
        taskHistory.setCreatedById(currentUserId);
        taskHistory.setCreated(OffsetDateTime.now());
        List<TaskHistoryLog> taskHistoryLogs = taskHistory.getTaskHistoryLogs();
        logTitleChange(taskHistory, currentTask.getTitle(), updateTaskRequest.title(), taskHistoryLogs);
        logDescriptionChange(taskHistory, currentTask.getDescription(), updateTaskRequest.description(), taskHistoryLogs);
        logStatusChange(taskHistory, currentTask.getStatus(), updateTaskRequest.status(), taskHistoryLogs);
        logReporterIdChange(taskHistory, currentTask.getReporterId(), updateTaskRequest.reporterId(), taskHistoryLogs);
        logPriorityChange(taskHistory, currentTask.getPriority(), updateTaskRequest.priority(), taskHistoryLogs);
        logDueDateChange(taskHistory, currentTask.getDueDate(), updateTaskRequest.dueDate(), taskHistoryLogs);
        logAssignedToChange(taskHistory, currentTask.getAssignedToId(), updateTaskRequest.assignedToId(), taskHistoryLogs);
        if (!taskHistoryLogs.isEmpty()) {
            taskHistoryRepository.save(taskHistory);
        }
    }

    private void logTitleChange(TaskHistory taskHistory, String currentTitle, String updatedTitle, List<TaskHistoryLog> taskHistoryLogs) {
        if (!Objects.equals(currentTitle, updatedTitle)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.TITLE);
            taskHistoryLog.setContentBefore(currentTitle);
            taskHistoryLog.setContentAfter(updatedTitle);
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logDescriptionChange(TaskHistory taskHistory, String currentDescription, String updatedDescription, List<TaskHistoryLog> taskHistoryLogs) {
        if (!Objects.equals(currentDescription, updatedDescription)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.DESCRIPTION);
            taskHistoryLog.setContentBefore(currentDescription);
            taskHistoryLog.setContentAfter(updatedDescription);
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logStatusChange(TaskHistory taskHistory, TaskStatus currentStatus, TaskStatus updatedStatus, List<TaskHistoryLog> taskHistoryLogs) {
        if (!currentStatus.equals(updatedStatus)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.STATUS);
            taskHistoryLog.setContentBefore(currentStatus.name());
            taskHistoryLog.setContentAfter(updatedStatus.name());
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logReporterIdChange(TaskHistory taskHistory, Long currentReporterId, Long updatedReporterId, List<TaskHistoryLog> taskHistoryLogs) {
        if (!Objects.equals(currentReporterId, updatedReporterId)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.REPORTER_ID);
            taskHistoryLog.setContentBefore(currentReporterId.toString());
            taskHistoryLog.setContentAfter(updatedReporterId.toString());
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logPriorityChange(TaskHistory taskHistory, TaskPriority currentPriorityId, TaskPriority updatedPriorityId, List<TaskHistoryLog> taskHistoryLogs) {
        if (currentPriorityId != updatedPriorityId) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.PRIORITY);
            taskHistoryLog.setContentBefore(currentPriorityId.name());
            taskHistoryLog.setContentAfter(updatedPriorityId.name());
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logDueDateChange(TaskHistory taskHistory, LocalDate currentDueDate, LocalDate updatedDueDate, List<TaskHistoryLog> taskHistoryLogs) {
        if ((currentDueDate == null && updatedDueDate != null) || (currentDueDate != null && updatedDueDate == null) || !currentDueDate.equals(updatedDueDate)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.DUE_DATE);
            if (currentDueDate != null)
                taskHistoryLog.setContentBefore(currentDueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            if (updatedDueDate != null)
                taskHistoryLog.setContentAfter(updatedDueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

    private void logAssignedToChange(TaskHistory taskHistory, Long currentAssignedToId, Long updatedAssignedToId, List<TaskHistoryLog> taskHistoryLogs) {
        if (!Objects.equals(currentAssignedToId, updatedAssignedToId)) {
            TaskHistoryLog taskHistoryLog = new TaskHistoryLog();
            taskHistoryLog.setTaskFieldName(TaskFieldName.ASSIGNED_TO);
            taskHistoryLog.setContentBefore(currentAssignedToId.toString());
            taskHistoryLog.setContentAfter(updatedAssignedToId.toString());
            taskHistory.addTaskHistoryLog(taskHistoryLog);
        }
    }

}
