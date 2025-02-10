package com.crm.service.serviceImpl.unit.taskServiceImpl;

import com.crm.dao.TaskRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.service.serviceImpl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SaveSubTaskTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    private Task subTask;
    private Task parentTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parentTask = new Task();
        parentTask.setId(1);
        subTask = new Task();
        subTask.setId(2);
        subTask.setParentTask(parentTask);

        User userTaskCreator = new User();
        userTaskCreator.setId(1);
        subTask.setUserTaskCreator(userTaskCreator);

        User assignedUser = new User();
        assignedUser.setId(2);
        subTask.setAssignedUserTask(assignedUser);
    }

    @Test
    void shouldSaveSubtaskSuccessfully() {
        // given
        User userTaskCreator = new User();
        userTaskCreator.setId(1);
        subTask.setUserTaskCreator(userTaskCreator);

        User assignedUser = new User();
        assignedUser.setId(2);
        subTask.setAssignedUserTask(assignedUser);

        when(taskRepository.findById(1)).thenReturn(Optional.of(parentTask));
        when(userRepository.findById(1)).thenReturn(Optional.of(userTaskCreator));
        when(userRepository.findById(2)).thenReturn(Optional.of(assignedUser));
        when(taskRepository.save(any(Task.class))).thenReturn(subTask);

        // when
        Task savedSubtask = underTest.saveSubtask(subTask);

        // then
        assertThat(savedSubtask).isEqualTo(subTask);
        verify(taskRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).findById(2);
        verify(taskRepository, times(1)).save(subTask);
    }
}
