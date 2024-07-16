package com.management.task.repository;

import com.management.task.model.taskhistory.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link TaskHistory} entities.
 * Provides methods to retrieve and manipulate task history data from the database.
 */
@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    /**
     * Retrieves a page of {@link TaskHistory} entities associated with a specific task,
     * ordered by creation date in descending order.
     *
     * @param taskId   the ID of the task for which task histories are retrieved.
     * @param pageable pagination information including page number, size, and sorting.
     * @return a page of {@link TaskHistory} entities for the specified task,
     * ordered by creation date descending.
     */
    Page<TaskHistory> findByTaskIdOrderByCreatedDesc(Long taskId, Pageable pageable);
}
