package com.crm.dao;

import com.crm.entity.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository underTest;

    @BeforeEach
    void setUp() {
        Ticket ticket1 = new Ticket();
        ticket1.setTopic("Urgent Ticket");
        ticket1.setDescription("This is an urgent ticket.");
        underTest.save(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setTopic("General Ticket");
        ticket2.setDescription("This is a general ticket.");
        underTest.save(ticket2);
    }

    @Test
    void existsByTopic_shouldReturnTrueIfTopicExists() {
        // given
        String topic = "Urgent Ticket";

        // when
        boolean exists = underTest.existsByTopic(topic);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByTopic_shouldReturnFalseIfTopicDoesNotExist() {
        // given
        String topic = "Nonexistent Ticket";

        // when
        boolean exists = underTest.existsByTopic(topic);

        // then
        assertThat(exists).isFalse();
    }
}