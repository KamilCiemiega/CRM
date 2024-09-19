package com.crm.service;

import com.crm.controller.dto.MessageRoleDTO;

import java.util.List;
import java.util.Optional;

public interface MessageRoleService {
    MessageRoleDTO save(MessageRoleDTO roleDTO);
    List<MessageRoleDTO> findAllRoles();
    Optional<MessageRoleDTO> findById(int roleId);
    void deleteRole(int roleId);
}
