package com.crm.util;

import com.crm.entity.*;

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

    public static Ticket createdTicket(
            List<Message> messages,
            List<UserNotification> userNotifications,
            Attachment attachment,
            Client ticketClient,
            User ticketUser)
    {
        Ticket ticket = new Ticket();
        ticket.setTopic("Topic");
        ticket.setType(Ticket.TicketType.MESSAGE);
        ticket.setStatus(Ticket.TicketStatus.PENDING);
        ticket.setDescription("Test description");
        ticket.setMessages(messages);
        ticket.setUserNotifications(userNotifications);
        ticket.getAttachments().add(attachment);
        ticket.setClient(ticketClient);
        ticket.setUser(ticketUser);

        return ticket;
    }
}
