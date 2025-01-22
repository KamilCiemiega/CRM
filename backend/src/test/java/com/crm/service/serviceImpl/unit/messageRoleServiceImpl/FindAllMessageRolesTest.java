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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FindAllMessageRolesTest {
    @Mock
    private MessageRoleRepository messageRoleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindAllMessageRoles() {
        // given
        List<MessageRole> roles = new ArrayList<>();
        roles.add(new MessageRole());
        roles.add(new MessageRole());

        when(messageRoleRepository.findAll()).thenReturn(roles);

        // when
        List<MessageRole> foundRoles = underTest.findAllRoles();

        // then
        verify(messageRoleRepository, times(1)).findAll();
        assertThat(foundRoles).hasSize(2);
    }
}
