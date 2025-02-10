package com.crm.service.serviceImpl.util;

import com.crm.entity.*;

import java.util.ArrayList;
import java.util.List;

public class TicketServiceTestDataHelper {

    public static User assignedUserToTicket(){
        User user = new User();
        user.setId(1);

        return user;
    }

    public static Client assignedClientToTicket(){
        Client client = new Client();
        client.setId(1);

        return client;
    }

    public static User existingUser(){
        User user = new User();
        user.setId(2);

        return user;
    }

    public static  Client existingClient(){
        Client client = new Client();
        client.setId(2);

        return client;
    }

    public static Message existingMessage(){
        Message message = new Message();
        message.setId(1);

        return message;
    }

    public static Attachment attachment(){
        Attachment attachment = new Attachment();
        attachment.setId(1);

        return attachment;
    }

    public static UserNotification userNotificationWithExistingUser(User exUser){
        UserNotification userNotification = new UserNotification();
        userNotification.setId(1);
        userNotification.setUser(exUser);

        return userNotification;
    }


    public static class TicketTestSetup {
        public Message existingMessage;
        public Attachment attachment;
        public User ticketUser;
        public User existingUser;
        public Client ticketClient;
        public Client existingClient;
        public UserNotification userNotificationWithExistingUser;

        public Ticket ticket;
    }

    public static TicketTestSetup prepareDefaultTicketTestSetup() {
        TicketTestSetup setup = new TicketTestSetup();

        setup.existingMessage = existingMessage();
        setup.attachment = attachment();
        setup.ticketUser = assignedUserToTicket();
        setup.existingUser = existingUser();
        setup.ticketClient = assignedClientToTicket();
        setup.existingClient = existingClient();
        setup.userNotificationWithExistingUser = userNotificationWithExistingUser(
                setup.existingUser);

        List<UserNotification> userNotificationList = new ArrayList<>();
        userNotificationList.add(setup.userNotificationWithExistingUser);

        setup.ticket = createTicket(
                List.of(setup.existingMessage),
                userNotificationList
        );
        return setup;
    }

    public static Ticket createTicket(
            List<Message> messages,
            List<UserNotification> userNotifications)
    {
        Ticket ticket = new Ticket();
        ticket.setTopic("Topic");
        ticket.setType(Ticket.TicketType.MESSAGE);
        ticket.setStatus(Ticket.TicketStatus.PENDING);
        ticket.setDescription("Test description");
        ticket.setMessages(messages);
        ticket.setUserNotifications(userNotifications);
        ticket.getAttachments().add(attachment());
        ticket.setClient(assignedClientToTicket());
        ticket.setUser(assignedUserToTicket());

        return ticket;
    }
}
