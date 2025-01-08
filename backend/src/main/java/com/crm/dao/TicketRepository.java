package com.crm.dao;

import com.crm.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    boolean existsByTopic(String topic);
}
