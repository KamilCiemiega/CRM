package com.crm.dao;

import com.crm.entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportingRepository extends JpaRepository<Reporting, Integer> {
    boolean existsByTopic(String topic);
}
