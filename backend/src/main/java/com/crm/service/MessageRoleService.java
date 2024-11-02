package com.crm.service;

import com.crm.controller.dto.MessageRoleDTO;
import com.crm.entity.MessageRole;

import java.util.List;
import java.util.Optional;

public interface MessageRoleService {
    MessageRole save(MessageRole role);
    List<MessageRole> findAllRoles();
    MessageRole update(int messageRoleId, MessageRole role);
    MessageRole findById(int roleId);
    MessageRole deleteRole(int roleId);
}
