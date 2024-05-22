package com.crm.service.serviceImpl;

import com.crm.dao.RoleRepository;
import com.crm.entity.Role;
import com.crm.entity.User;
import com.crm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findById(User user) {
        Optional<Role> role = roleRepository.findById(user.getRole().getId());

        return role;
    }
}
