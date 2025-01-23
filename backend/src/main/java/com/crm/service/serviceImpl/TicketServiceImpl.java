package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.EntityFinder;
import com.crm.service.TaskService;
import com.crm.service.TicketService;
import com.crm.utils.EntityProcessorHelper;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            List<Integer> messageIds = ticket.getMessages().stream()
                    .map(Message::getId)
                    .toList();
            List<Message> messages = messageRepository.findAllByIds(messageIds);
            ticket.setMessages(messages);
        }

        if (!ticket.getAttachments().isEmpty()) {
            ticket.getAttachments().forEach(attachment -> attachment.setTicket(ticket));
        }

        if (!ticket.getUserNotifications().isEmpty()) {
            List<UserNotification> userNotifications = ticket.getUserNotifications();
            List<Integer> userIds = userNotifications.stream()
                    .map(notification -> notification.getUser().getId())
                    .distinct()
                    .toList();

            List<User> existingUsers = userRepository.findAllByIds(userIds);
            Set<Integer> existingUserIds = existingUsers.stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());

            userNotifications.removeIf(notification -> !existingUserIds.contains(notification.getUser().getId()));
            Map<Integer, User> userMap = existingUsers.stream()
                    .collect(Collectors.toMap(User::getId, user -> user));

            ticket.getUserNotifications().forEach(notification -> {
                User user = userMap.get(notification.getUser().getId());
                if (user != null){
                    notification.setUser(user);
                    notification.setTicketNotification(ticket);
                }
            });
        }

        if (ticket.getClient() == null || ticket.getClient().getId() == null) {
            throw new IllegalArgumentException("Ticket must have a valid client.");
        }
        Client existingClient = findEntity(clientRepository, ticket.getClient().getId(), "Client");
        ticket.setClient(existingClient);
        existingClient.getTickets().add(ticket);

        if (ticket.getUser() == null || ticket.getUser().getId() == null) {
            throw new IllegalArgumentException("Ticket must have a valid user.");
        }
        User existingUser = findEntity(userRepository, ticket.getUser().getId(), "User");
        ticket.setUser(existingUser);
        existingUser.getTickets().add(ticket);

        return ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public Ticket updateTicket(Integer ticketId, Ticket updatedTicket) {
        Ticket existingTicket = findEntity(ticketRepository, ticketId, "Ticket");

        if (!existingTicket.getTopic().equals(updatedTicket.getTopic())) {
            existingTicket.setTopic(updatedTicket.getTopic());
        }
        existingTicket.setStatus(updatedTicket.getStatus());
        existingTicket.setType(updatedTicket.getType());
        existingTicket.setDescription(updatedTicket.getDescription());

        if (!existingTicket.getClient().getId().equals(updatedTicket.getClient().getId())) {
            Client client = findEntity(clientRepository, updatedTicket.getClient().getId(), "Client");
            existingTicket.setClient(client);
        }
        if (!existingTicket.getUser().getId().equals(updatedTicket.getUser().getId())) {
            User user = findEntity(userRepository, updatedTicket.getUser().getId(), "User");
            existingTicket.setUser(user);
        }

        existingTicket.setMessages(findEntities(updatedTicket.getMessages(), messageRepository, "Message"));

        existingTicket.getTasks().clear();
        existingTicket.getTasks().addAll(processTasks(updatedTicket.getTasks(), existingTicket));

        existingTicket.getUserNotifications().clear();
        existingTicket.getUserNotifications().addAll(processUserNotifications(updatedTicket.getUserNotifications(), existingTicket));

        existingTicket.getAttachments().clear();
        existingTicket.getAttachments().addAll(processAttachments(updatedTicket.getAttachments(), existingTicket));

        return ticketRepository.save(existingTicket);
    }

    @Transactional
    @Override
    public Ticket deleteTicket(Integer tickedId) {
        Ticket foundetTicket = findEntity(ticketRepository, tickedId, "Ticket");
        ticketRepository.delete(foundetTicket);

        return foundetTicket;
    }

    private List<Task> processTasks(List<Task> tasks, Ticket ticket) {
        return tasks.stream()
                .map(task -> {
                    Task managedTask = findEntity(taskRepository, task.getId(), "Task");
                    managedTask.setTicket(ticket);
                    return managedTask;
                })
                .toList();
    }

    private List<UserNotification> processUserNotifications(List<UserNotification> notifications, Ticket ticket) {
        return new EntityProcessorHelper().processUserNotifications(
                notifications,
                ticket,
                userNotificationRepository,
                userRepository,
                UserNotification::setTicketNotification
        );
    }

    private List<Attachment> processAttachments(List<Attachment> attachments, Ticket ticket) {
        return  new EntityProcessorHelper().processAttachments(
                attachments,
                ticket,
                attachmentRepository,
                Attachment::setTicket
        );
    }
}