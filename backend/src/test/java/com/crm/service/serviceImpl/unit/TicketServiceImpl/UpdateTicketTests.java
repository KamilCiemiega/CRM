package com.crm.service.serviceImpl.unit.TicketServiceImpl;

import com.crm.dao.*;
import com.crm.service.serviceImpl.TicketServiceImpl;
import com.crm.util.TicketServiceTestDataHelper;
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

public class UpdateTicketTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    UserNotificationRepository userNotificationRepository;
    @Mock
    AttachmentRepository attachmentRepository;
    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    private TicketServiceImpl underTest;

    Ticket existingTicket;
    Ticket updatedTicket;
    User assignedUserToTicket;
    Client assignedClientToTicket;
    Task task;
    UserNotification userNotificationWithoutId;
    Attachment attachmentWithoutId;

    TicketServiceTestDataHelper.TicketTestSetup setup;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        setup = TicketServiceTestDataHelper.prepareDefaultTicketTestSetup();
        existingTicket = setup.ticket;
        existingTicket.setId(1);

        //***UpdateTicket section***

        //User
        assignedUserToTicket = new User();
        assignedUserToTicket.setId(3);
        //Client
        assignedClientToTicket = new Client();
        assignedClientToTicket.setId(3);
        //Task
        task = new Task();
        task.setId(1);
        //UserNotification
        userNotificationWithoutId= new UserNotification();
        userNotificationWithoutId.setUser(setup.existingUser);
        //Attachment
        attachmentWithoutId = new Attachment();
        attachmentWithoutId.setType(Attachment.Type.TICKET);

        //Ticket
        updatedTicket = new Ticket();
        updatedTicket.setTopic("New topic");
        updatedTicket.setStatus(Ticket.TicketStatus.COMPLETED);
        updatedTicket.setType(Ticket.TicketType.MEETING);
        updatedTicket.setDescription("New description");
        updatedTicket.setClient(assignedClientToTicket);
        updatedTicket.setUser(assignedUserToTicket);
        updatedTicket.setMessages(List.of(setup.existingMessage));
        updatedTicket.setTasks(List.of(task));
        updatedTicket.setUserNotifications(List.of(
                setup.userNotificationWithExistingUser,
                userNotificationWithoutId
        ));
        updatedTicket.setAttachments(List.of(
                setup.attachment,
                attachmentWithoutId
        ));
    }

    private void mockRepositories(){
        when(messageRepository.findById(1)).thenReturn(Optional.of(setup.existingMessage));
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(userNotificationRepository.findById(1)).thenReturn(Optional.of(setup.userNotificationWithExistingUser));
        when(attachmentRepository.findById(1)).thenReturn(Optional.of(setup.attachment));
        when(clientRepository.findById(3)).thenReturn(Optional.of(assignedClientToTicket));
        when(userRepository.findById(2)).thenReturn(Optional.of(setup.existingUser));
        when(userRepository.findById(3)).thenReturn(Optional.of(assignedUserToTicket));
        when(ticketRepository.findById(1)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    private void verifyRepositoryInteractions(){
        verify(ticketRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).findById(anyInt());
        verify(userRepository, times(2)).findById(anyInt());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void shouldUpdateTicket(){
        //given
        //when
        mockRepositories();
        Ticket savedTicket = underTest.updateTicket(1, updatedTicket);

        //then
        verifyRepositoryInteractions();

        assertThat(savedTicket).isNotNull();
        assertThat(savedTicket.getTopic()).isEqualTo("New topic");
        assertThat(savedTicket.getClient()).isEqualTo(assignedClientToTicket);
        assertThat(savedTicket.getUser()).isEqualTo(assignedUserToTicket);

        Task updatedTask = savedTicket.getTasks().get(0);
        assertThat(updatedTask).isEqualTo(task);
        assertThat(updatedTask.getTicket()).isEqualTo(savedTicket);

        assertThat(savedTicket.getUserNotifications()).hasSize(2);
        assertThat(savedTicket.getAttachments()).hasSize(2);
        assertThat(task.getTicket()).isEqualTo(savedTicket);
    }

    @Test
    void shouldSetOnlyCorrectTask(){
        //given
        Task nonExistingTask = new Task();
        nonExistingTask.setId(2);

        updatedTicket.setTasks(List.of(task, nonExistingTask));

        //when
        mockRepositories();
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.findById(2)).thenReturn(Optional.empty());


        Ticket savedTicket = underTest.updateTicket(1, updatedTicket);

        //then
        verify(taskRepository, times(2)).findById(anyInt());
        assertThat(savedTicket.getTasks()).hasSize(1);
    }
}
