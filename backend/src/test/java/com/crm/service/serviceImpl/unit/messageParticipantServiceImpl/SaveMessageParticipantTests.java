package com.crm.service.serviceImpl.unit.messageParticipantServiceImpl;

import com.crm.dao.MessageParticipantRepository;
import com.crm.entity.MessageParticipant;
import com.crm.service.serviceImpl.MessageParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class SaveMessageParticipantTests {
    @Mock
    private MessageParticipantRepository messageParticipantRepository;

    @InjectMocks
    private MessageParticipantServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveMessageParticipantSuccessfully() {
        // given
        MessageParticipant participant = new MessageParticipant();
        participant.setId(1);

        when(messageParticipantRepository.save(participant)).thenReturn(participant);

        // when
        MessageParticipant savedParticipant = underTest.save(participant);

        // then
        verify(messageParticipantRepository, times(1)).save(participant);
        assertThat(savedParticipant).isEqualTo(participant);
    }
}
