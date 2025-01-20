package com.crm.service.serviceImpl;

import com.crm.dao.MessageRoleRepository;
import com.crm.entity.MessageRole;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.MessageRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MessageRoleServiceImpl implements MessageRoleService {

    private final MessageRoleRepository roleRepository;

    @Autowired
    public MessageRoleServiceImpl(MessageRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public MessageRole save(MessageRole role) {return roleRepository.save(role);}

    @Transactional
    @Override
    public MessageRole update(int messageRoleId, MessageRole role) {
        MessageRole existingRole = roleRepository.findById(messageRoleId)
                .orElseThrow(() -> new NoSuchEntityException("messageRole not found for ID: " + messageRoleId));

        existingRole.setMessage(role.getMessage());
        existingRole.setStatus(role.getStatus());
        existingRole.setParticipant(role.getParticipant());

        return roleRepository.save(existingRole);
    }

    @Override
    public List<MessageRole> findAllRoles() {return roleRepository.findAll();}

    @Override
    public MessageRole findById(int roleId) {
        return roleRepository.findById(roleId)
                 .orElseThrow(() -> new NoSuchEntityException("messageRole not found for ID: " + roleId));
    }

    @Transactional
    @Override
    public MessageRole deleteRole(int roleId) {
        MessageRole existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchEntityException("messageRole not found for ID: " + roleId));

        roleRepository.delete(existingRole);
        return existingRole;
    }
}
