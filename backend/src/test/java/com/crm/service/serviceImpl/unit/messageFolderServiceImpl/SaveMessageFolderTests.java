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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaveMessageFolderTests {
    @Mock
    MessageFolderRepository messageFolderRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    MessageFolderServiceImpl underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveFolderWithUserAndParentFolder() {
        // given
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        MessageFolder parentFolder = new MessageFolder();
        parentFolder.setId(2);
        when(messageFolderRepository.findById(2)).thenReturn(Optional.of(parentFolder));

        MessageFolder folder = new MessageFolder();
        folder.setName("Test Folder");
        folder.setUser(user);
        folder.setParentFolder(parentFolder);

        when(messageFolderRepository.save(any(MessageFolder.class))).thenAnswer(invocation -> {
            MessageFolder savedFolder = invocation.getArgument(0);
            savedFolder.setId(100);
            return savedFolder;
        });

        // when
        MessageFolder result = underTest.save(folder);

        // then
        verify(userRepository, times(1)).findById(1);
        verify(messageFolderRepository, times(1)).findById(2);
        verify(messageFolderRepository, times(1)).save(folder);

        assertThat(result.getId()).isEqualTo(100);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getParentFolder()).isEqualTo(parentFolder);
    }

    @Test
    void shouldThrowExceptionWhenFolderNameIsEmpty() {
        // given
        MessageFolder folder = new MessageFolder();
        folder.setName("");

        // when & then
        assertThatThrownBy(() -> underTest.save(folder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Folder name cannot be null or empty.");
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        // given
        MessageFolder folder = new MessageFolder();
        folder.setName("Test Folder");
        folder.setUser(null);

        // when & then
        assertThatThrownBy(() -> underTest.save(folder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Owner user ID must be provided.");
    }
}
