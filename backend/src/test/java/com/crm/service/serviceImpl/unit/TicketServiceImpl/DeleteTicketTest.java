package com.crm.service.serviceImpl.unit.TicketServiceImpl;

import com.crm.dao.TicketRepository;
import com.crm.service.serviceImpl.TicketServiceImpl;
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

public class DeleteTicketTest {
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketServiceImpl underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteTicketWhenExists() {
        // given
        Ticket existingTicket = new Ticket();
        existingTicket.setId(1);

        when(ticketRepository.findById(1)).thenReturn(Optional.of(existingTicket));

        // when
        Ticket deletedTicket = underTest.deleteTicket(1);

        // then
        assertThat(deletedTicket).isNotNull();
        assertThat(deletedTicket.getId()).isEqualTo(1);

        verify(ticketRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).delete(existingTicket);
    }
}
