package com.crm.service;

import com.crm.entity.User;

public interface UserService {
    void save(User theUser);

    User findById(int theId);
}
