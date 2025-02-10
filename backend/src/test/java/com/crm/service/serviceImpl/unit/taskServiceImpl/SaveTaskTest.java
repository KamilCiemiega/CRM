package com.crm.service.serviceImpl.unit.taskServiceImpl;

import com.crm.dao.TaskRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
import com.crm.entity.Ticket;
import com.crm.entity.User;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SaveTaskTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    private Task task;
    private Ticket ticket;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);

        User assignedUser = new User();
        assignedUser.setId(2);

        ticket = new Ticket();
        ticket.setId(1);

        task = new Task();
        task.setId(1);
        task.setUserTaskCreator(user);
        task.setAssignedUserTask(assignedUser);
        task.setTicket(ticket);
    }

    @Test
    void shouldSaveTaskSuccessfully() {
        // given
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // when
        Task savedTask = underTest.saveTask(task);

        // then
        assertThat(savedTask).isEqualTo(task);
        verify(ticketRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(task);
    }
}
