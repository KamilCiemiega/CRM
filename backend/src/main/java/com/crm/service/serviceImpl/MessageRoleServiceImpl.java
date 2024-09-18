package com.crm.service.serviceImpl;

import com.crm.controller.dto.MessageRoleDTO;
import com.crm.dao.MessageRoleReposiitory;
import com.crm.entity.MessageRole;
import com.crm.service.MessageRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageRoleServiceImpl implements MessageRoleService {

    private final MessageRoleReposiitory roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MessageRoleServiceImpl(MessageRoleReposiitory roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageRoleDTO save(MessageRoleDTO roleDTO) {
        MessageRole role = modelMapper.map(roleDTO, MessageRole.class);
        MessageRole savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, MessageRoleDTO.class);
    }

    @Override
    public List<MessageRoleDTO> findAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(role, MessageRoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MessageRoleDTO> findById(int roleId) {
        return roleRepository.findById(roleId)
                .map(role -> modelMapper.map(role, MessageRoleDTO.class));
    }

    @Override
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }
}
