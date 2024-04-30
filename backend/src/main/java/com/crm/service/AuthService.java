package com.crm.service;

import com.crm.entity.User;

import java.util.Optional;

public interface AuthService {
    void save(User theUser);
    Optional<User> findByEmailAndPassword(String email, String password);

    User findByEmail(String email);
}
