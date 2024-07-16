package com.management.task.model.task;

import com.management.task.model.common.search.SimpleCriteriaSearchRequest;
import com.management.task.type.TaskPriority;
import com.management.task.type.TaskStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskSearchRequest extends SimpleCriteriaSearchRequest<Task> {
    public static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "dueDate");

    @Size(max = 50, message = "Title {jakarta.validation.constraints.Size.message}")
    private String title;
    @Size(max = 100, message = "Description {jakarta.validation.constraints.Size.message}")
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate fromDueDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate toDueDate;

    public TaskSearchRequest() {
        super(Task.class, DEFAULT_SORT);
    }

    @Override
    protected List<Predicate> getPredicates(CriteriaBuilder builder, Root<Task> root, Authentication auth) {
        List<Predicate> predicates = new ArrayList<>();

        if (this.title != null) {
            predicates.add(builder.like(builder.upper(root.get(Task_.title)), "%" + this.title.toUpperCase() + "%"));
        }
        if (this.description != null) {
            predicates.add(builder.like(builder.upper(root.get(Task_.description)), "%" + this.description.toUpperCase() + "%"));
        }
        if (this.status != null) {
            predicates.add(builder.equal(root.get(Task_.status), this.status));
        }
        if (this.priority != null) {
            predicates.add(builder.equal(root.get(Task_.priority), this.priority));
        }
        if (this.fromDueDate != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Task_.dueDate), this.fromDueDate));
        }
        if (this.toDueDate != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Task_.dueDate), this.toDueDate));
        }
        return predicates;
    }

}
