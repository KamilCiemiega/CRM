package com.crm.service.serviceImpl.unit.messageFolderServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.MessageService;
import com.crm.service.serviceImpl.MessageFolderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeleteAllMessagesFromFolderTests {
    @Mock
    private MessageFolderRepository messageFolderRepository;
    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageFolderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteAllMessagesFromFolderSuccessfully() {
        // given
        int folderId = 1;

        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);

        Message message1 = new Message();
        message1.setId(1);

        Message message2 = new Message();
        message2.setId(2);

        folder.getMessages().add(message1);
        folder.getMessages().add(message2);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));
        when(messageService.deleteMessage(anyInt())).thenAnswer(invocation -> {
            int messageId = invocation.getArgument(0);
            Message deletedMessage = new Message();
            deletedMessage.setId(messageId);
            return deletedMessage;
        });

        // when
        List<Message> deletedMessages = underTest.deleteAllMessagesFromFolder(folderId);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageService, times(2)).deleteMessage(anyInt());
        verify(messageFolderRepository, times(1)).save(folder);

        assertThat(deletedMessages).hasSize(2);
        assertThat(deletedMessages).extracting(Message::getId).containsExactlyInAnyOrder(1, 2);
        assertThat(folder.getMessages()).isEmpty();
    }

    @Test
    void shouldHandleEmptyFolderGracefully() {
        // given
        int folderId = 1;

        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));

        // when
        List<Message> deletedMessages = underTest.deleteAllMessagesFromFolder(folderId);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verifyNoInteractions(messageService);
        verify(messageFolderRepository, times(1)).save(folder);

        assertThat(deletedMessages).isEmpty();
        assertThat(folder.getMessages()).isEmpty();
    }
}
