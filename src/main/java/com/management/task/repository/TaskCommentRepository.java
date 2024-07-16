package com.management.task.repository;

import com.management.task.model.taskdiscussion.TaskComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link TaskComment} entities.
 * Provides methods to retrieve and manipulate task comment data from the database.
 */
@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    /**
     * Retrieves a page of {@link TaskComment} entities associated with a specific task,
     * ordered by creation date in descending order.
     *
     * @param taskId   the ID of the task for which task comments are retrieved.
     * @param pageable pagination information including page number, size, and sorting.
     * @return a page of {@link TaskComment} entities for the specified task,
     * ordered by creation date in descending order.
     */
    Page<TaskComment> findByTaskIdOrderByCreatedDesc(Long taskId, Pageable pageable);
}
