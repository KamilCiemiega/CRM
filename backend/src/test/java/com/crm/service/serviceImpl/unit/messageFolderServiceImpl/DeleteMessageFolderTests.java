package com.crm.service.serviceImpl.unit.messageFolderServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.DeleteDefaultFolderException;
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

public class DeleteMessageFolderTests {
    @Mock
    private MessageFolderRepository messageFolderRepository;

    @InjectMocks
    private MessageFolderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteFolderSuccessfully() {
        // given
        int folderId = 1;
        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);
        folder.setFolderType(MessageFolder.FolderType.USER);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));

        // when
        MessageFolder deletedFolder = underTest.deleteFolder(folderId);

        // then
        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageFolderRepository, times(1)).deleteById(folderId);
        assertThat(deletedFolder).isEqualTo(folder);
    }

    @Test
    void shouldThrowExceptionWhenFolderIsSystem() {
        // given
        int folderId = 1;
        MessageFolder folder = new MessageFolder();
        folder.setId(folderId);
        folder.setFolderType(MessageFolder.FolderType.SYSTEM);

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.of(folder));

        // when & then
        assertThatThrownBy(() -> underTest.deleteFolder(folderId))
                .isInstanceOf(DeleteDefaultFolderException.class)
                .hasMessageContaining("Cannot delete the default folder or folder not found for ID: " + folderId);

        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageFolderRepository, never()).deleteById(folderId);
    }

    @Test
    void shouldThrowExceptionWhenFolderNotFound() {
        // given
        int folderId = 1;

        when(messageFolderRepository.findById(folderId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> underTest.deleteFolder(folderId))
                .isInstanceOf(DeleteDefaultFolderException.class)
                .hasMessageContaining("Cannot delete the default folder or folder not found for ID: " + folderId);

        verify(messageFolderRepository, times(1)).findById(folderId);
        verify(messageFolderRepository, never()).deleteById(folderId);
    }
}
