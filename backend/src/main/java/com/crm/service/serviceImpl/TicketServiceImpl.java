package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.EntityFinder;
import com.crm.service.TaskService;
import com.crm.service.TicketService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService, EntityFinder {

    private final TicketRepository ticketRepository;
    private final MessageRepository messageRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, MessageRepository messageRepository, ClientRepository clientRepository, UserRepository userRepository, UserNotificationRepository userNotificationRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository, TaskService taskService) {
        this.ticketRepository = ticketRepository;
        this.messageRepository = messageRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional
    @Override
    public Ticket save(Ticket ticket) {
        if (ticketRepository.existsByTopic(ticket.getTopic())) {
            throw new IllegalArgumentException("Topic must be unique. Value already exists: " + ticket.getTopic());
        }

        if (!ticket.getMessages().isEmpty()) {
            List<Message> messages = findEntities(ticket.getMessages(), messageRepository, "Message");
            ticket.setMessages(messages);
        }

        if (!ticket.getAttachments().isEmpty()) {
            ticket.getAttachments().forEach(attachment -> attachment.setTicket(ticket));
        }

        if (!ticket.getUserNotifications().isEmpty()) {
            ticket.getUserNotifications().stream()
                    .peek(notification -> {
                        User user = findEntity(userRepository, notification.getUser().getId(), "User");
                        notification.setUser(user);
                    })
                    .forEach(notification -> notification.setTicketNotification(ticket));
        }

        Client existingClient = findEntity(clientRepository, ticket.getClient().getId(), "Client");
        ticket.setClient(existingClient);
        existingClient.getTickets().add(ticket);

        User existingUser = findEntity(userRepository, ticket.getUser().getId(), "User");
        ticket.setUser(existingUser);
        existingUser.getTickets().add(ticket);

        return ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public Ticket updateTicket(Integer ticketId, Ticket updatedTicket) {
        Ticket existingTicket = findEntity(ticketRepository, ticketId, "Ticket");

        if (!existingTicket.getTopic().equals(updatedTicket.getTopic())){
            existingTicket.setTopic(updatedTicket.getTopic());
        }
        existingTicket.setStatus(updatedTicket.getStatus());
        existingTicket.setType(updatedTicket.getType());
        existingTicket.setDescription(updatedTicket.getDescription());

        if (updatedTicket.getClient() != null) {
            Client client = findEntity(clientRepository, updatedTicket.getClient().getId(), "Client");
            existingTicket.setClient(client);
        }
        if (updatedTicket.getUser() != null) {
            User user = findEntity(userRepository, updatedTicket.getUser().getId(), "User");
            existingTicket.setUser(user);
        }

        existingTicket.getMessages().clear();
        existingTicket.setMessages(findEntities(updatedTicket.getMessages(), messageRepository, "Message"));

//        existingTicket.setTasks(updatedTicket.getTasks().stream()
//                .peek(task -> task.setTicket(existingTicket))
//                .toList());

        existingTicket.getUserNotifications().clear();
        existingTicket.setUserNotifications(updatedTicket.getUserNotifications().stream()
                .peek(notification -> {
                    User user = findEntity(userRepository, notification.getUser().getId(), "User");
                    notification.setUser(user);
                    notification.setTicketNotification(existingTicket);
                })
                .toList());

        existingTicket.getAttachments().clear();
        existingTicket.setAttachments(updatedTicket.getAttachments().stream()
                .peek(attachment -> attachment.setTicket(existingTicket))
                .toList());

        Ticket ticketS = ticketRepository.save(existingTicket);

        return ticketS;
    }

}