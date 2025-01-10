package com.crm.service;

import com.crm.entity.Ticket;
import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    Ticket save(Ticket ticket);
    Ticket updateTicket(Integer ticketId, Ticket ticket);
}
