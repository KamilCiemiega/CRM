package com.crm.service.serviceImpl.unit.ticketServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.TicketServiceImpl;
import com.crm.service.serviceImpl.util.TicketServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.entity.*;

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
    TicketServiceTestDataHelper.TicketTestSetup setup;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        setup = TicketServiceTestDataHelper.prepareDefaultTicketTestSetup();
        ticket = setup.ticket;
    }

    private void mockRepositories() {
        when(messageRepository.findAllByIds(List.of(1))).thenReturn(List.of(setup.existingMessage));
        when(userRepository.findAllByIds(List.of(2))).thenReturn(List.of(setup.existingUser));
        when(clientRepository.findById(1)).thenReturn(Optional.of(setup.ticketClient));
        when(userRepository.findById(1)).thenReturn(Optional.of(setup.ticketUser));
    }

    private void verifyRepositoryInteractions() {
        verify(messageRepository, times(1)).findAllByIds(List.of(1));
        verify(userRepository, times(1)).findAllByIds(List.of(2));
        verify(userRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
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
        assertThat(savedTicket.getAttachments().get(0).getTicket()).isEqualTo(savedTicket);
        //userNotifications check
        UserNotification userNotificationReference = savedTicket.getUserNotifications().get(0);
        assertThat(userNotificationReference.getUser()).isEqualTo(setup.existingUser);
        assertThat(userNotificationReference.getTicketNotification()).isEqualTo(savedTicket);
        //User & Client check
        assertThat(setup.ticketClient.getTickets().get(0)).isEqualTo(savedTicket);
        assertThat(setup.ticketUser.getTickets().get(0)).isEqualTo(savedTicket);
    }

    @Test
    void shouldTrowIllegalArgumentException_whenTopicIsNotUnique(){
        //given
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
        Client nonExistingClient = new Client();
        nonExistingClient.setId(3);

        ticket.setClient(nonExistingClient);

        when(clientRepository.findById(3)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.save(ticket))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Client not found for ID: 3");

        verify(clientRepository, times(1)).findById(3);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // given
        User nonExistingUser = new User();
        nonExistingUser.setId(3);

        ticket.setUser(nonExistingUser);

        when(clientRepository.findById(1)).thenReturn(Optional.of(setup.ticketClient));
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.save(ticket))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("User not found for ID: 3");

        verify(userRepository, times(1)).findById(3);
    }

    @Test
    void shouldFilterOutUserNotificationWithNonExistingUser() {
        // given
        User nonExistingUser = new User();
        nonExistingUser.setId(3);
        UserNotification userNotificationWithNonExistingUser = new UserNotification();
        userNotificationWithNonExistingUser.setUser(nonExistingUser);

        ticket.getUserNotifications().add(userNotificationWithNonExistingUser);

        when(clientRepository.findById(1)).thenReturn(Optional.of(setup.ticketClient));
        when(userRepository.findById(1)).thenReturn(Optional.of(setup.ticketUser));
        when(userRepository.findAllByIds(List.of(2,3))).thenReturn(List.of(setup.existingUser));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Ticket savedTicket = underTest.save(ticket);

        // then
        assertThat(savedTicket.getUserNotifications()).hasSize(1);
        assertThat(savedTicket.getUserNotifications().get(0).getUser()).isEqualTo(setup.existingUser);
    }

}
