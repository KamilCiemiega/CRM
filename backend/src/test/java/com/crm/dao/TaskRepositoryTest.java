package com.crm.dao;

import com.crm.entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository underTest;

    @BeforeEach
    void setUp() {
        Task task1 = new Task();
        task1.setTopic("Important Task");
        task1.setDescription("This is an important task.");
        underTest.save(task1);

        Task task2 = new Task();
        task2.setTopic("Secondary Task");
        task2.setDescription("This is a secondary task.");
        underTest.save(task2);
    }

    @Test
    void existsByTopic_shouldReturnTrueIfTopicExists() {
        // given
        String topic = "Important Task";

        // when
        boolean exists = underTest.existsByTopic(topic);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByTopic_shouldReturnFalseIfTopicDoesNotExist() {
        // given
        String topic = "Nonexistent Task";

        // when
        boolean exists = underTest.existsByTopic(topic);

        // then
        assertThat(exists).isFalse();
    }
}