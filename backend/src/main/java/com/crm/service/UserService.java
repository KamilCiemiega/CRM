package com.crm.service;

import com.crm.controller.dto.NewUserDTO;
import com.crm.controller.dto.UserDTO;
import com.crm.entity.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User User);
    User updateUser(int userId, User User);
    UserDTO login(NewUserDTO newUserDTO, HttpServletRequest request);
    void changePassword(User user, String newPassword);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
    void createPasswordResetTokenForUser(User user, String passwordResetToken);
    List <User> findAllUsers();
    Optional<User> findUserByPasswordToken(String passwordResetToken);
    String createPasswordResetRequest(String email, String applicationUrl) throws MessagingException, UnsupportedEncodingException;
    String resetPassword(String newPassword, String token);
}
