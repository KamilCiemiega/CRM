package com.crm.service.serviceImpl.unit.messageParticipantServiceImpl;

import com.crm.dao.MessageParticipantRepository;
import com.crm.service.serviceImpl.MessageParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.entity.MessageParticipant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class FindMessageParticipantByUserOrClientIdTests {
    @Mock
    private MessageParticipantRepository messageParticipantRepository;

    @InjectMocks
    private MessageParticipantServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindUserByParticipantTypeAndId() {
        // given
        int userId = 1;
        MessageParticipant participant = new MessageParticipant();
        participant.setType(MessageParticipant.ParticipantType.USER);

        when(messageParticipantRepository.findByUserId(userId)).thenReturn(participant);

        // when
        MessageParticipant result = underTest.findUserOrClientById(MessageParticipant.ParticipantType.USER, userId, null);

        // then
        verify(messageParticipantRepository, times(1)).findByUserId(userId);
        assertThat(result).isEqualTo(participant);
    }


    @Test
    void shouldFindClientByParticipantTypeAndId() {
        // given
        int clientId = 1;
        MessageParticipant participant = new MessageParticipant();
        participant.setType(MessageParticipant.ParticipantType.CLIENT);

        when(messageParticipantRepository.findByClientId(clientId)).thenReturn(participant);

        // when
        MessageParticipant result = underTest.findUserOrClientById(MessageParticipant.ParticipantType.CLIENT, null, clientId);

        // then
        verify(messageParticipantRepository, times(1)).findByClientId(clientId);
        assertThat(result).isEqualTo(participant);
    }
}
