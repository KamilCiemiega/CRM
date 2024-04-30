package com.crm.controller;

import com.crm.entity.User;
import com.crm.exception.UserNotFoundException;
import com.crm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = authService.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody User theUser) {
        String plainPassword = theUser.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);

        theUser.setPassword(encodedPassword);

        authService.save(theUser);

        return ResponseEntity.ok("User saved successfully");
    }

    @PostMapping("reset-password")
    public void resetPassword(@RequestBody String email) {
        User user = authService.findByEmail(email);

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Wyślij e-mail z linkiem do resetowania hasła
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;
        String emailBody = "Click the following link to reset your password: " + resetLink;
        sendEmail(email, "Password Reset", emailBody);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
