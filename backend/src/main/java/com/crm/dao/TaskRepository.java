package com.crm.dao;

import com.crm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByTopic(String topic);
}
