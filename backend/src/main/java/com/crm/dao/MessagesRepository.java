package com.crm.dao;

import com.crm.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages,Integer> {
}
