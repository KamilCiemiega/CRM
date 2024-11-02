package com.crm.service.serviceImpl;

import com.crm.dao.MessageFolderRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Message;
import com.crm.entity.MessageFolder;
import com.crm.entity.User;
import com.crm.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageFolderServiceImplTest {

    @Mock
    private MessageFolderRepository messageFolderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageService messageService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MessageFolderServiceImpl underTest;

    private MessageFolder messageFolder;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("John");

        messageFolder = new MessageFolder();
        messageFolder.setId(1);
        messageFolder.setName("Inbox");
        messageFolder.setUser(user);
        messageFolder.setFolderType(MessageFolder.FolderType.USER);
    }

    @Test
    void findAllMessageFolders() {
    }

    @Test
    void save() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(messageFolderRepository.save(messageFolder)).thenReturn(messageFolder);

        MessageFolder savedFolder = underTest.save(messageFolder);

        assertThat(savedFolder).isEqualTo(messageFolder);
        verify(messageFolderRepository).save(messageFolder);
    }

    @Test
    void updateMessageFolder() {
        // given
        int folderId = 1;

        MessageFolder existingFolder = new MessageFolder();
        existingFolder.setId(folderId);
        existingFolder.setName("Old Folder");
        existingFolder.setFolderType(MessageFolder.FolderType.SYSTEM);

        MessageFolder updatedFolder = new MessageFolder();
        updatedFolder.setId(folderId);
        updatedFolder.setName("Updated Folder");
        updatedFolder.setFolderType(MessageFolder.FolderType.USER);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(existingFolder));
        when(messageFolderRepository.save(existingFolder)).thenReturn(updatedFolder);

        // when
        MessageFolder result = underTest.updateMessageFolder(folderId, updatedFolder);

        // then
        verify(messageFolderRepository).findById(folderId);
        verify(messageFolderRepository).save(existingFolder);

        assertThat(result.getName()).isEqualTo(updatedFolder.getName());
        assertThat(result.getFolderType()).isEqualTo(updatedFolder.getFolderType());
    }


    @Test
    void deleteFolder() {
        when(messageFolderRepository.findById(1)).thenReturn(Optional.of(messageFolder));

        MessageFolder deletedFolder = underTest.deleteFolder(1);

        assertThat(deletedFolder).isEqualTo(messageFolder);
        verify(messageFolderRepository).deleteById(1);
    }

    @Test
    void deleteAllMessagesFromFolder() {
        Message message = new Message();
        message.setId(1);
        List<Message> messages = List.of(message);

        messageFolder.setMessages(messages);
        when(messageFolderRepository.findById(1)).thenReturn(Optional.of(messageFolder));
        when(messageService.deleteMessage(1)).thenReturn(message);

        List<Message> deletedMessages = underTest.deleteAllMessagesFromFolder(1);

        assertThat(deletedMessages).isEqualTo(messages);
        verify(messageService).deleteMessage(1);
    }
}