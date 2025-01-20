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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FindMessageRoleByIdTests {
    @Mock
    private MessageRoleRepository messageRoleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindMessageRoleById() {
        // given
        int roleId = 1;
        MessageRole role = new MessageRole();
        role.setId(roleId);

        when(messageRoleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // when
        MessageRole foundRole = underTest.findById(roleId);

        // then
        verify(messageRoleRepository, times(1)).findById(roleId);
        assertThat(foundRole).isEqualTo(role);
    }
}
