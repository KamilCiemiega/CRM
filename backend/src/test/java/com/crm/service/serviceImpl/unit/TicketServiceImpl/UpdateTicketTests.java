package com.crm.service.serviceImpl.unit.TicketServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageRepository;
import com.crm.dao.TicketRepository;
import com.crm.dao.UserRepository;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.TicketServiceImpl;
import com.crm.util.TicketServiceTestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.entity.*;

import java.util.ArrayList;
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
    @InjectMocks
    private TicketServiceImpl underTest;

    Ticket ticket;
    Ticket updatedTicket;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        updatedTicket.setTopic("New topic");
        updatedTicket.setStatus(Ticket.TicketStatus.COMPLETED);
        updatedTicket.setType(Ticket.TicketType.MEETING);
        updatedTicket.setDescription("New description");

    }

    @Test
    void shouldUpdateTicket(){
        //given

        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        //when
        //then
    }
}
