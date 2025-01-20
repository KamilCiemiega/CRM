package com.crm.service.serviceImpl.unit.messageServiceImpl;

import com.crm.dao.MessageFolderRepository;
import com.crm.dao.MessageRepository;
import com.crm.entity.Message;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.serviceImpl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class DeleteMessageTests {
    @Mock
    MessageRepository messageRepository;
    @Mock
    MessageFolderRepository messageFolderRepository;

    @InjectMocks
    MessageServiceImpl underTest;

    Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        message = new Message();
        message.setId(1);
    }

    @Test
    void shouldDeleteMessageSuccessfully() {
        // given
        when(messageRepository.findById(anyInt())).thenReturn(Optional.of(message));

        // when
        underTest.deleteMessage(1);

        // then
        verify(messageRepository, times(1)).delete(message);
    }

    @Test
    void shouldThrowExceptionWhenMessageNotFoundForDeletion() {
        // given
        when(messageRepository.findById(anyInt())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.deleteMessage(1))
                .isInstanceOf(NoSuchEntityException.class)
                .hasMessageContaining("Message not found");
    }
}
