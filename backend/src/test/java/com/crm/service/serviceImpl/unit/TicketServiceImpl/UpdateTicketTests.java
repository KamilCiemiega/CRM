package com.crm.service.serviceImpl.unit.TicketServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.TicketServiceImpl;
import com.crm.util.TicketServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;
public class UpdateTicketTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketServiceImpl underTest;

    Ticket ticket;
    Message existingMessage;
    Message nonExistingMessage;
    Attachment attachment;
    User existingUserInUserNotification;
    User nonExistingUserInUserNotification;
    User ticketUser;
    Client ticketClient;
    UserNotification userNotificationWithExistingUser;
    UserNotification userNotificationWithNonExistingUser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        //messages
        existingMessage = TicketServiceTestDataHelper.existingMessage();
        nonExistingMessage = TicketServiceTestDataHelper.nonExistingMessage();
        //attachment
        attachment = TicketServiceTestDataHelper.attachment();
        //users
        existingUserInUserNotification = TicketServiceTestDataHelper.existingUserInUserNotification();
        nonExistingUserInUserNotification = TicketServiceTestDataHelper.nonExistingUserInUserNotification();
        ticketUser = TicketServiceTestDataHelper.assignedUserToTicket();
        ticketClient = TicketServiceTestDataHelper.assignedClientToTicket();
        //userNotification
        userNotificationWithExistingUser = TicketServiceTestDataHelper.userNotificationWithExistingUser(existingUserInUserNotification);
        userNotificationWithNonExistingUser = TicketServiceTestDataHelper.userNotificationWithNonExistingUser(nonExistingUserInUserNotification);
        List<UserNotification> userNotificationList = new ArrayList<>();
        userNotificationList.add(userNotificationWithExistingUser);
        userNotificationList.add(userNotificationWithNonExistingUser);

        ticket = TicketServiceTestDataHelper.createdTicket(
                List.of(existingMessage, nonExistingMessage),
                userNotificationList,
                attachment,
                ticketClient,
                ticketUser);
    }

    @Test
    void shouldUpdateTicket(){
        //given
        ticket.setId(1);

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        //when
        //then
    }
}
