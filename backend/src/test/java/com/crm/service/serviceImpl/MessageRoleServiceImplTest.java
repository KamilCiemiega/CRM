package com.crm.service.serviceImpl;

import com.crm.dao.MessageRoleRepository;
import com.crm.entity.MessageParticipant;
import com.crm.entity.MessageRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageRoleServiceImplTest {

    @Mock
    private MessageRoleRepository roleRepository;

    @InjectMocks
    private MessageRoleServiceImpl underTest;

    private MessageRole messageRole;


    @BeforeEach
    void setUp() {
        messageRole = new MessageRole();
        messageRole.setId(1);
        messageRole.setStatus(MessageRole.RoleStatus.CC);

        MessageParticipant participant = new MessageParticipant();
        participant.setId(1);
        messageRole.setParticipant(participant);
    }

    @Test
    void save() {
        // given
        when(roleRepository.save(messageRole)).thenReturn(messageRole);

        // when
        MessageRole savedRole = underTest.save(messageRole);

        // then
        verify(roleRepository, times(1)).save(messageRole);
        assertNotNull(savedRole);
        assertEquals(messageRole.getId(), savedRole.getId());
    }

    @Test
    void update() {
        // given
        int messageRoleId = 1;
        MessageRole updatedRole = new MessageRole();
        updatedRole.setStatus(MessageRole.RoleStatus.TO);

        when(roleRepository.findById(messageRoleId)).thenReturn(Optional.of(messageRole));
        when(roleRepository.save(messageRole)).thenReturn(messageRole);

        // when
        MessageRole result = underTest.update(messageRoleId, updatedRole);

        // then
        verify(roleRepository, times(1)).findById(messageRoleId);
        verify(roleRepository, times(1)).save(messageRole);
        assertEquals(updatedRole.getStatus(), result.getStatus());
    }

    @Test
    void findAllRoles() {
        // given
        List<MessageRole> roles = List.of(messageRole);
        when(roleRepository.findAll()).thenReturn(roles);

        // when
        List<MessageRole> result = underTest.findAllRoles();

        // then
        verify(roleRepository, times(1)).findAll();
        assertNotNull(result);
    }

    @Test
    void findById() {
        // given
        int roleId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(messageRole));

        // when
        MessageRole foundRole = underTest.findById(roleId);

        // then
        verify(roleRepository, times(1)).findById(roleId);
        assertNotNull(foundRole);
        assertEquals(messageRole.getId(), foundRole.getId());
    }
    @Test
    void deleteRole() {
        // given
        int roleId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(messageRole));

        // when
        MessageRole deletedRole = underTest.deleteRole(roleId);

        // then
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).delete(messageRole);
        assertEquals(messageRole, deletedRole);
    }
}