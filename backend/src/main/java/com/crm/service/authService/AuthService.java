package com.crm.service.authService;

import com.crm.entity.User;

import java.util.Optional;

public interface AuthService {
    void save(User theUser);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}
