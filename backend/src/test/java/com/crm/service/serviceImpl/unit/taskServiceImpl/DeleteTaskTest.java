package com.crm.service.serviceImpl.unit.taskServiceImpl;
import com.crm.dao.TaskRepository;
import com.crm.entity.Task;
import com.crm.service.serviceImpl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteTaskTest {

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
        task.setTopic("Test Task");
    }

        @Test
        void shouldDeleteTaskSuccessfully() {
            // given
            when(taskRepository.findById(1)).thenReturn(Optional.of(task));

            // when
            Task deletedTask = underTest.deleteTask(1);

            // then
            assertThat(deletedTask).isEqualTo(task);

            verify(taskRepository, times(1)).findById(1);
            verify(taskRepository, times(1)).delete(task);
        }
}
