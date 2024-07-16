package com.management.task.repository;

import com.management.task.model.task.Task;
import com.management.task.repository.common.SearchRequestRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on Task entities,
 * and custom search operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>,
        SearchRequestRepositoryCustom<Task> {
    /**
     * Retrieves a page of tasks assigned to a specific user ID.
     *
     * @param assignedToId The ID of the user to whom tasks are assigned.
     * @param pageable     Pagination and sorting information.
     * @return A Page containing tasks assigned to the specified user.
     */
    Page<Task> findByAssignedToId(Long assignedToId, Pageable pageable);
}
