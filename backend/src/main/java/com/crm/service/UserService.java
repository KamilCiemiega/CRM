package com.crm.service;

import com.crm.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);

    void changePassword(User user, String newPassword);
    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);

    Optional<User> findUserByPasswordToken(String passwordResetToken);

}
