package com.crm.service.serviceImpl.unit.messageRoleServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.exception.DeleteDefaultFolderException;
import com.crm.service.serviceImpl.MessageFolderServiceImpl;
import com.crm.service.serviceImpl.MessageRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DeleteMessageRoleTests {
    @Mock
    private MessageRoleRepository messageRoleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldDeleteMessageRoleSuccessfully() {
        // given
        int roleId = 1;
        MessageRole role = new MessageRole();
        role.setId(roleId);

        when(messageRoleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // when
        MessageRole deletedRole = underTest.deleteRole(roleId);

        // then
        verify(messageRoleRepository, times(1)).findById(roleId);
        verify(messageRoleRepository, times(1)).delete(role);
        assertThat(deletedRole).isEqualTo(role);
    }
}
