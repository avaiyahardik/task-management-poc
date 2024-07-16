package com.management.task.repository;

import com.management.task.model.taskhistory.Setup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetupRepository extends JpaRepository<Setup, String> {

}
