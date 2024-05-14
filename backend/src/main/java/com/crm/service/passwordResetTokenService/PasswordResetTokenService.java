package com.crm.service.passwordResetTokenService;

import com.crm.entity.PasswordResetToken;
import com.crm.entity.User;

import java.util.Optional;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(User user, String passwordToken);
    String validatePasswordResetToken(String passwordResetToken);

    Optional<User> findUserByPasswordToken(String passwordResetToken);

    PasswordResetToken findPasswordResetToken(String token);
}
