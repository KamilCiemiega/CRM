package com.crm.service.serviceImpl.unit.taskServiceImpl;

import com.crm.dao.TaskRepository;
import com.crm.entity.Task;
import com.crm.service.serviceImpl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetAllTasksTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1);
    }

    @Test
    void shouldReturnAllTasks() {
        // given
        List<Task> expectedTasks = List.of(task);
        when(taskRepository.findAll()).thenReturn(expectedTasks);

        // when
        List<Task> actualTasks = underTest.getAllTasks();

        // then
        assertThat(actualTasks).isEqualTo(expectedTasks);
        verify(taskRepository, times(1)).findAll();
    }
}