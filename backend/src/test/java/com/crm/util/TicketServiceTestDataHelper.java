package com.crm.util;

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

    public static Message existingMessage(){
        Message message = new Message();
        message.setId(1);

        return message;
    }

    public static Message nonExistingMessage(){
        Message message = new Message();
        message.setId(2);

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

    public static User existingUserInUserNotification(){
        User user = new User();
        user.setId(1);

        return user;
    }

    public static UserNotification userNotificationWithNonExistingUser(User noUser){
        UserNotification userNotification = new UserNotification();
        userNotification.setId(2);
        userNotification.setUser(noUser);

        return userNotification;
    }

    public static User nonExistingUserInUserNotification(){
        User user = new User();
        user.setId(5);

        return user;
    }

    public static class TicketTestSetup {
        public Message existingMessage;
        public Message nonExistingMessage;
        public Attachment attachment;
        public User ticketUser;
        public Client ticketClient;
        public User existingUserInUserNotification;
        public User nonExistingUserInUserNotification;
        public UserNotification userNotificationWithExistingUser;
        public UserNotification userNotificationWithNonExistingUser;

        public Ticket ticket;
    }

    public static TicketTestSetup prepareDefaultTicketTestSetup() {
        TicketTestSetup setup = new TicketTestSetup();

        setup.existingMessage = existingMessage();
        setup.nonExistingMessage = nonExistingMessage();
        setup.attachment = attachment();
        setup.ticketUser = assignedUserToTicket();
        setup.ticketClient = assignedClientToTicket();
        setup.existingUserInUserNotification = existingUserInUserNotification();
        setup.nonExistingUserInUserNotification = nonExistingUserInUserNotification();
        setup.userNotificationWithExistingUser = userNotificationWithExistingUser(setup.existingUserInUserNotification);
        setup.userNotificationWithNonExistingUser = userNotificationWithNonExistingUser(setup.nonExistingUserInUserNotification);

        List<UserNotification> userNotificationList = new ArrayList<>();
        userNotificationList.add(setup.userNotificationWithExistingUser);
        userNotificationList.add(setup.userNotificationWithNonExistingUser);

        setup.ticket = createdTicket(
                List.of(setup.existingMessage, setup.nonExistingMessage),
                userNotificationList
        );
        return setup;
    }

    public static Ticket createdTicket(
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
