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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class SaveMessageRoleTest {
    @Mock
    private MessageRoleRepository messageRoleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveMessageRoleSuccessfully() {
        // given
        MessageRole role = new MessageRole();
        role.setId(1);
        role.setStatus(MessageRole.RoleStatus.TO);

        when(messageRoleRepository.save(role)).thenReturn(role);

        // when
        MessageRole savedRole = underTest.save(role);

        // then
        verify(messageRoleRepository, times(1)).save(role);
        assertThat(savedRole).isEqualTo(role);
    }
}
