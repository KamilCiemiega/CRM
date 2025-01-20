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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateMessageFolderTests {

    @Mock
    private MessageFolderRepository messageFolderRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MessageFolderServiceImpl underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateMessageFolderSuccessfully() {
        // given
        int folderId = 1;

        MessageFolder existingFolder = new MessageFolder();
        existingFolder.setId(folderId);
        existingFolder.setName("Old Folder Name");
        existingFolder.setFolderType(MessageFolder.FolderType.USER);

        MessageFolder parentFolder = new MessageFolder();
        parentFolder.setId(2);
        parentFolder.setName("Parent Folder");

        MessageFolder updatedFolder = new MessageFolder();
        updatedFolder.setName("New Folder Name");
        updatedFolder.setParentFolder(parentFolder);
        updatedFolder.setFolderType(MessageFolder.FolderType.USER);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(existingFolder));
        when(messageFolderRepository.findById(2)).thenReturn(Optional.of(parentFolder));
        when(messageFolderRepository.save(any(MessageFolder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        MessageFolder result = underTest.updateMessageFolder(folderId, updatedFolder);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageFolderRepository, times(1)).findById(2);
        verify(messageFolderRepository, times(1)).save(existingFolder);

        assertThat(result.getName()).isEqualTo("New Folder Name");
        assertThat(result.getParentFolder()).isEqualTo(parentFolder);
        assertThat(result.getFolderType()).isEqualTo(MessageFolder.FolderType.USER);
    }

    @Test
    void shouldUpdateMessageFolderWithoutParentFolder() {
        // given
        int folderId = 1;

        MessageFolder existingFolder = new MessageFolder();
        existingFolder.setId(folderId);

        MessageFolder updatedFolder = new MessageFolder();
        updatedFolder.setName("New Folder Name");
        updatedFolder.setParentFolder(null);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(existingFolder));
        when(messageFolderRepository.save(any(MessageFolder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        MessageFolder result = underTest.updateMessageFolder(folderId, updatedFolder);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageFolderRepository, never()).findById(eq(2));
        verify(messageFolderRepository, times(1)).save(existingFolder);

        assertThat(result.getName()).isEqualTo("New Folder Name");
        assertThat(result.getParentFolder()).isNull();
    }

}
