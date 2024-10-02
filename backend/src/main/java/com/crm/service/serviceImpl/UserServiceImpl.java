package com.crm.service.serviceImpl;

import com.crm.controller.dto.NewUserDTO;
import com.crm.controller.dto.UserDTO;
import com.crm.dao.UserRepository;
import com.crm.entity.User;
import com.crm.exception.NoSuchEntityException;
import com.crm.service.PasswordResetTokenService;
import com.crm.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetTokenService passwordResetTokenService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, PasswordResetTokenService passwordResetTokenService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenService = passwordResetTokenService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        String plainPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(int userId, User userToUpdate) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchEntityException("User not found with id: " + userId));

        if (userToUpdate.getPassword() != null && !userToUpdate.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userToUpdate.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        existingUser.setFirstName(userToUpdate.getFirstName());
        existingUser.setLastName(userToUpdate.getLastName());
        existingUser.setEmail(userToUpdate.getEmail());
        existingUser.setRole(userToUpdate.getRole());

        return userRepository.save(existingUser);
    }


    @Override
    public UserDTO login(NewUserDTO newUserDTO, HttpServletRequest request) {
        User user = userRepository.findByEmail(newUserDTO.getEmail())
                .orElseThrow(() -> new NoSuchEntityException("Can't find user with email " + newUserDTO.getEmail()));

        if (!passwordEncoder.matches(newUserDTO.getPassword(), user.getPassword())) {
            throw new NoSuchEntityException("Can't find user with password " + newUserDTO.getPassword());
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        HttpSession session = request.getSession();
        session.setAttribute("user", userDTO);

        return userDTO;
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
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
