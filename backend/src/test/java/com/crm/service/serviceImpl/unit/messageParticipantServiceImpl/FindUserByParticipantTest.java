package com.crm.service.serviceImpl.unit.messageParticipantServiceImpl;

import com.crm.dao.*;
import com.crm.entity.MessageParticipant;
import com.crm.entity.User;
import com.crm.service.serviceImpl.MessageParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


public class FindUserByParticipantTest {
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
    void shouldFindUserByParticipantId() {
        // given
        int participantId = 1;
        int userId = 1;

        MessageParticipant participant = new MessageParticipant();
        participant.setId(participantId);
        participant.setType(MessageParticipant.ParticipantType.USER);

        User user = new User();
        user.setId(userId);

        participant.setUser(user);

        when(messageParticipantRepository.findById(participantId)).thenReturn(Optional.of(participant));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        Object result = underTest.findUserOrClientByParticipantId(participantId);

        // then
        verify(messageParticipantRepository, times(1)).findById(participantId);
        verify(userRepository, times(1)).findById(userId);
        assertThat(result).isEqualTo(user);
    }
}
