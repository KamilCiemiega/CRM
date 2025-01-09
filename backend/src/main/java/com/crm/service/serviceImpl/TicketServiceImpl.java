package com.crm.service.serviceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.TicketService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MessageRepository messageRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TicketServiceImpl.class);

    public TicketServiceImpl(TicketRepository ticketRepository, MessageRepository messageRepository, ClientRepository clientRepository, UserRepository userRepository, UserNotificationRepository userNotificationRepository, AttachmentRepository attachmentRepository, TaskRepository taskRepository) {
        this.ticketRepository = ticketRepository;
        this.messageRepository = messageRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
    }

    private <T> T findEntity(JpaRepository<T, Integer> repository, int entityId, String entityName){
        return repository.findById(entityId)
                .orElseThrow(() -> new NoSuchEntityException(entityName + " not found for ID: " + entityId));
    }

    private <T> List<T> findEntities(List<T> entities, JpaRepository<T, Integer> repository, String entityName) {
        List<T> foundEntities = new ArrayList<>();
        for (T entity : entities) {
            Integer id = extractId(entity);
            T foundEntity = findEntity(repository, id, entityName);
            foundEntities.add(foundEntity);
        }
        return foundEntities;
    }

    private Integer extractId(Object entity) {
        if (entity instanceof Message) {
            return ((Message) entity).getId();
        } else if (entity instanceof UserNotification) {
            return ((UserNotification) entity).getId();
        } else if (entity instanceof Attachment) {
            return ((Attachment) entity).getId();
        }
        throw new IllegalArgumentException("Unknown entity type");
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional
    @Override
    public Ticket save(Ticket ticket) {
        if(ticketRepository.existsByTopic(ticket.getTopic())){
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

//        if (!ticket.getTasks().isEmpty()){
//           List<Task> taskList = ticket.getTasks().stream()
//                    .peek(task -> {
//                       User taskCreator = findEntity(userRepository,task.getUserTaskCreator().getId(), "Task creator");
//                       task.setUserTaskCreator(taskCreator);
//                       User taskWorker = findEntity(userRepository,task.getUserTaskWorker().getId(), "Task worker");
//                       task.setUserTaskWorker(taskWorker);
//                       task.setTicket(ticket);
//                    })
//                    .toList();
//           ticket.getTasks().clear();
//           ticket.setTasks(taskList);
//        }

        Client existingClient = findEntity(clientRepository, ticket.getClient().getId(), "Client");
        ticket.setClient(existingClient);

        User existingUser = findEntity(userRepository, ticket.getUser().getId(), "User");
        ticket.setUser(existingUser);

        return ticketRepository.save(ticket);
    }

    @Transactional
    @Override
    public Ticket updateTicket(int ticketId, Ticket ticket) {
        return null;
    }

}
