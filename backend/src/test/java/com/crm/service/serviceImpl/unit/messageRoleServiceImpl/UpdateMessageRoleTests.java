package com.crm.service.serviceImpl.unit.messageRoleServiceImpl;

import com.crm.dao.*;
import com.crm.entity.*;
import com.crm.service.serviceImpl.MessageRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UpdateMessageRoleTests {
    @Mock
    private MessageRoleRepository messageRoleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldUpdateMessageRoleSuccessfully() {
        // given
        int roleId = 1;
        MessageRole existingRole = new MessageRole();
        existingRole.setId(roleId);
        existingRole.setStatus(MessageRole.RoleStatus.TO);

        MessageRole updatedRole = new MessageRole();
        updatedRole.setStatus(MessageRole.RoleStatus.CC);

        when(messageRoleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
        when(messageRoleRepository.save(existingRole)).thenReturn(existingRole);

        // when
        MessageRole result = underTest.update(roleId, updatedRole);

        // then
        verify(messageRoleRepository, times(1)).findById(roleId);
        verify(messageRoleRepository, times(1)).save(existingRole);
        assertThat(result.getStatus()).isEqualTo(MessageRole.RoleStatus.CC);
    }
}
