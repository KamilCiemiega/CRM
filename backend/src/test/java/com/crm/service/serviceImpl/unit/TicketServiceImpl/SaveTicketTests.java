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

public class SaveTicketTests {
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

    private void mockRepositories() {
        when(messageRepository.findAllByIds(List.of(1, 2))).thenReturn(List.of(existingMessage));
        when(userRepository.findAllByIds(List.of(1, 5))).thenReturn(List.of(existingUserInUserNotification));
        when(clientRepository.findById(1)).thenReturn(Optional.of(ticketClient));
        when(userRepository.findById(1)).thenReturn(Optional.of(ticketUser));
    }

    private void verifyRepositoryInteractions() {
        verify(messageRepository, times(1)).findAllByIds(List.of(1, 2));
        verify(userRepository, times(1)).findAllByIds(List.of(1, 5));
        verify(userRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    private void verifyUserNotification(Ticket savedTicket){
        UserNotification userNotificationReference = savedTicket.getUserNotifications().get(0);
        assertThat(savedTicket.getUserNotifications()).hasSize(1);
        assertThat(userNotificationReference.getUser()).isEqualTo(existingUserInUserNotification);
        assertThat(userNotificationReference.getTicketNotification()).isEqualTo(savedTicket);
    }

    private void verifyUserAndClient(Ticket savedTicket){
        assertThat(ticketClient.getTickets()).hasSize(1);
        assertThat(ticketClient.getTickets().get(0)).isEqualTo(savedTicket);
        assertThat(ticketUser.getTickets()).hasSize(1);
        assertThat(ticketUser.getTickets().get(0)).isEqualTo(savedTicket);
    }

    @Test
    void shouldSaveTicket() {
        // given
        mockRepositories();
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket savedTicket = invocation.getArgument(0);
            savedTicket.setId(1);
            return savedTicket;
        });

        //when
        Ticket savedTicket = underTest.save(ticket);

        // then
        verifyRepositoryInteractions();

        //messages check
        assertThat(savedTicket.getMessages()).hasSize(1);
        assertThat(savedTicket.getMessages().get(0).getId()).isEqualTo(1);
        //attachments check
        assertThat(attachment.getTicket()).isEqualTo(savedTicket);
        //userNotifications check
        verifyUserNotification(savedTicket);
        //User & Client check
        verifyUserAndClient(savedTicket);
    }

    @Test
    void shouldTrowIllegalArgumentException_whenTopicIsNotUnique(){
        //given
        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTopic("Topic");
        //when
        when(ticketRepository.existsByTopic(ticket.getTopic())).thenReturn(true);

        //then
        assertThatThrownBy(() -> underTest.save(ticket))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Topic must be unique. Value already exists: Topic");
        verify(ticketRepository, times(1)).existsByTopic("Topic");
    }


    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        // given
        Ticket ticket = new Ticket();
        Client client = new Client();
        client.setId(1);

        ticket.setClient(client);

        when(clientRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.save(ticket))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Client not found for ID: 1");

        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        Ticket ticket = new Ticket();

        User user = new User();
        user.setId(1);
        Client client = new Client();
        client.setId(1);

        ticket.setUser(user);
        ticket.setClient(client);

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.save(ticket))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("User not found for ID: 1");

        verify(userRepository, times(1)).findById(1);
    }
}
