package com.crm.service.serviceImpl.unit.messageParticipantServiceImpl;

import com.crm.dao.MessageParticipantRepository;
import com.crm.service.serviceImpl.MessageParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.crm.entity.MessageParticipant;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FindClientByParticipantTypeAndIdTests {
    @Mock
    private MessageParticipantRepository messageParticipantRepository;

    @InjectMocks
    private MessageParticipantServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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


    @Test
    void shouldThrowExceptionForInvalidParticipantType() {
        // given
        int participantId = 1;

        MessageParticipant participant = new MessageParticipant();
        participant.setId(participantId);
        participant.setType(null); // Invalid type

        when(messageParticipantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        // when & then
        assertThatThrownBy(() -> underTest.findUserOrClientByParticipantId(participantId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid participant type");
    }
}
