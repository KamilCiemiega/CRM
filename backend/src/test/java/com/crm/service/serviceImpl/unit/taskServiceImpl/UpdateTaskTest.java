package com.crm.service.serviceImpl.unit.taskServiceImpl;

import com.crm.dao.AttachmentRepository;
import com.crm.dao.TaskRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserNotificationRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Attachment;
import com.crm.entity.Task;
import com.crm.entity.Ticket;
import com.crm.entity.User;
import com.crm.entity.UserNotification;
import com.crm.service.serviceImpl.TaskServiceImpl;
import com.crm.utils.EntityProcessorHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateTaskTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserNotificationRepository userNotificationRepository;
    @Mock
    private AttachmentRepository attachmentRepository;
    @Mock
    private EntityProcessorHelper entityProcessorHelper;

    @InjectMocks
    private TaskServiceImpl underTest;

    private Task existingTask;
    private Task updatedTask;
    private User creator;
    private User assignedUser;
    private Ticket ticket;
    private List<UserNotification> userNotifications;
    private List<Attachment> attachments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        creator = new User();
        creator.setId(1);

        assignedUser = new User();
        assignedUser.setId(2);

        ticket = new Ticket();
        ticket.setId(1);

        existingTask = new Task();
        existingTask.setId(1);
        existingTask.setTopic("Original Topic");
        existingTask.setDescription("Original Description");
        existingTask.setUserTaskCreator(creator);
        existingTask.setAssignedUserTask(assignedUser);
        existingTask.setTicket(ticket);

        updatedTask = new Task();
        updatedTask.setId(1);
        updatedTask.setTopic("Updated Topic");
        updatedTask.setDescription("Updated Description");
        updatedTask.setUserTaskCreator(creator);
        updatedTask.setAssignedUserTask(assignedUser);
        updatedTask.setTicket(ticket);

        userNotifications = List.of(new UserNotification());
        attachments = List.of(new Attachment());

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
    }

    @Nested
    class SuccessfulUpdates {
        @Test
        void shouldUpdateTaskSuccessfully() {
            // given
            when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

            // when
            Task result = underTest.updateTask(1, updatedTask);

            // then
            assertThat(result).isEqualTo(updatedTask);
            assertThat(existingTask.getTopic()).isEqualTo("Updated Topic");
            assertThat(existingTask.getDescription()).isEqualTo("Updated Description");

            verify(taskRepository, times(1)).findById(1);
            verify(taskRepository, times(1)).save(existingTask);
        }

        @Test
        void shouldChangeAssignedUser() {
            // given
            User newAssignedUser = new User();
            newAssignedUser.setId(3);
            updatedTask.setAssignedUserTask(newAssignedUser);

            when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
            when(userRepository.findById(3)).thenReturn(Optional.of(newAssignedUser));
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

            // when
            Task result = underTest.updateTask(1, updatedTask);

            // then
            assertThat(result.getAssignedUserTask().getId()).isEqualTo(3);
            verify(userRepository, times(1)).findById(3);
        }

        @Test
        void shouldChangeUserTaskCreator() {
            // given
            User newCreator = new User();
            newCreator.setId(4);
            updatedTask.setUserTaskCreator(newCreator);

            when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
            when(userRepository.findById(4)).thenReturn(Optional.of(newCreator));
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

            // when
            Task result = underTest.updateTask(1, updatedTask);

            // then
            assertThat(result.getUserTaskCreator().getId()).isEqualTo(4);
            verify(userRepository, times(1)).findById(4);
        }

        @Test
        void shouldUpdateTicket() {
            // given
            Ticket newTicket = new Ticket();
            newTicket.setId(5);
            updatedTask.setTicket(newTicket);

            when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
            when(ticketRepository.findById(5)).thenReturn(Optional.of(newTicket));
            when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

            // when
            Task result = underTest.updateTask(1, updatedTask);

            // then
            assertThat(result.getTicket().getId()).isEqualTo(5);
            verify(ticketRepository, times(1)).findById(5);
        }
    }
}
