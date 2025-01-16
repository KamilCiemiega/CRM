package com.crm.service.serviceImpl;

import com.crm.dao.RoleRepository;
import com.crm.dao.UserRepository;
import com.crm.entity.Role;
import com.crm.entity.User;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.PasswordResetTokenService;
import com.crm.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordResetTokenService passwordResetTokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordResetTokenService = passwordResetTokenService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        if (user.getRole() == null || user.getRole().getId() == null) {
            throw new IllegalArgumentException("Role ID must be provided for the user.");
        }

        Role role = roleRepository.findById(user.getRole().getId())
                .orElseThrow(() -> new NoSuchEntityException("Role not found for ID: " + user.getRole().getId()));

        user.setRole(role);

        user.setPassword(user.getPassword());

        return userRepository.save(user);
    }


    @Transactional
    @Override
    public User updateUser(int userId, User userToUpdate) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchEntityException("User not found with id: " + userId));

        if (userToUpdate.getPassword() != null && !userToUpdate.getPassword().isEmpty()) {
            existingUser.setPassword(userToUpdate.getPassword());
        }

        existingUser.setFirstName(userToUpdate.getFirstName());
        existingUser.setLastName(userToUpdate.getLastName());
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setRole(userToUpdate.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public User login(User user, HttpServletRequest request) {
        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NoSuchEntityException("Can't find user with email " + user.getEmail()));

        if (!user.getPassword().equals(existingUser.getPassword())) {
            throw new NoSuchEntityException("Invalid password");
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", existingUser);

        return existingUser;
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public String createPasswordResetRequest(String email, String applicationUrl) throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = findByEmail(email);
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user.get(), passwordResetToken);
            return passwordResetEmailLink(user.get(), applicationUrl, passwordResetToken);
        }
        return null;
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken) {
        return applicationUrl + "/api/auth/reset-password?token=" + passwordResetToken;
    }

    @Override
    public String resetPassword(String newPassword, String token) {
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> theUserOptional = findUserByPasswordToken(token);
        if (theUserOptional.isPresent()) {
            User user = theUserOptional.get();
            changePassword(user, newPassword);
            return "Password has been reset successfully";
        } else {
            return "Invalid password reset token";
        }
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
    }

    @Override
    public Optional<User> findUserByPasswordToken(String token) {
        return passwordResetTokenService.findUserByPasswordToken(token);
    }

}
