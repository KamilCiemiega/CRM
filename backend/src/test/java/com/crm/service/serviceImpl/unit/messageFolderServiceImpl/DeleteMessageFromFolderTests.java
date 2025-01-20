package com.crm.service.serviceImpl.unit.messageFolderServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.serviceImpl.MessageFolderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class DeleteMessageFromFolderTests {
    @Mock
    private MessageFolderRepository messageFolderRepository;
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageFolderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteMessageFromFolderSuccessfully() {
        // given
        int folderId = 1;
        int messageId = 1;

        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);

        Message message = new Message();
        message.setId(messageId);
        folder.getMessages().add(message);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // when
        Message deletedMessage = underTest.deleteMessageFromFolder(folderId, messageId);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageRepository, times(1)).findById(messageId);
        verify(messageFolderRepository, times(1)).save(folder);

        assertThat(deletedMessage).isEqualTo(message);
        assertThat(folder.getMessages()).doesNotContain(message);
    }

    @Test
    void shouldThrowExceptionWhenMessageNotInFolder() {
        // given
        int folderId = 1;
        int messageId = 1;

        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);

        Message message = new Message();
        message.setId(messageId);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        // when & then
        assertThatThrownBy(() -> underTest.deleteMessageFromFolder(folderId, messageId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Message not found in the specified folder");

        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageRepository, times(1)).findById(messageId);
        verify(messageFolderRepository, never()).save(any());
    }
}
