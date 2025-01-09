package com.crm.service;

import com.crm.entity.MessageRole;
import java.util.List;

public interface MessageRoleService {
    MessageRole save(MessageRole role);
    List<MessageRole> findAllRoles();
    MessageRole update(int messageRoleId, MessageRole role);
    MessageRole findById(int roleId);
    MessageRole deleteRole(int roleId);
}
