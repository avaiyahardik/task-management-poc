package com.management.task.repository;

import com.management.task.model.task.TaskArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskArchiveRepository extends JpaRepository<TaskArchive, Long> {

}
