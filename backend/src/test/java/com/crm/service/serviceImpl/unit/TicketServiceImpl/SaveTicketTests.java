package com.crm.service.serviceImpl.unit.TicketServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.service.serviceImpl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.MessageServiceImpl;
import com.crm.util.MessageServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.anyString;
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
    Message message;
    Message nonExistingMessage;
    Attachment attachment;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket();

        message = new Message();
        message.setId(1);

        nonExistingMessage = new Message();
        nonExistingMessage.setId(2);

        attachment = new Attachment();
        attachment.setId(1);

        ticket.setMessages(List.of(message,nonExistingMessage));
        ticket.getAttachments().add(attachment);
    }

    @Test
    void shouldSaveTicketWithExistingMessagesOnly() {
        // given
        when(messageRepository.findAllByIds(List.of(1, 2)))
                .thenReturn(List.of(message));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket savedTicket = invocation.getArgument(0);
            savedTicket.setId(1);
            return savedTicket;
        });

        // when
        Ticket savedTicket = underTest.save(ticket);

        // then
        verify(messageRepository, times(1)).findAllByIds(List.of(1, 2));
        verify(ticketRepository, times(1)).save(any(Ticket.class));

        assertThat(savedTicket.getMessages()).hasSize(1);
        assertThat(savedTicket.getMessages().get(0).getId()).isEqualTo(1);
        assertThat(attachment.getTicket()).isEqualTo(savedTicket);
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
}
