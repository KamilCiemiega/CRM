package com.crm.service.serviceImpl.unit.messageParticipantServiceImpl;

import com.crm.dao.ClientRepository;
import com.crm.dao.MessageParticipantRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Client;
import com.crm.entity.MessageParticipant;
import com.crm.service.serviceImpl.MessageParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class FindClientByParticipantTest {
    @Mock
    private MessageParticipantRepository messageParticipantRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private MessageParticipantServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindClientByParticipantId() {
        // given
        int participantId = 1;
        int clientId = 1;

        MessageParticipant participant = new MessageParticipant();
        participant.setId(participantId);
        participant.setType(MessageParticipant.ParticipantType.CLIENT);

        Client client = new Client();
        client.setId(clientId);

        participant.setClient(client);

        when(messageParticipantRepository.findById(participantId)).thenReturn(Optional.of(participant));
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // when
        Object result = underTest.findUserOrClientByParticipantId(participantId);

        // then
        verify(messageParticipantRepository, times(1)).findById(participantId);
        verify(clientRepository, times(1)).findById(clientId);
        assertThat(result).isEqualTo(client);
    }

}
