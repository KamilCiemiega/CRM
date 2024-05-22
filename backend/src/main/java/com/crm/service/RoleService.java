package com.crm.service;

import com.crm.entity.Role;
import com.crm.entity.User;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(User user);
}
