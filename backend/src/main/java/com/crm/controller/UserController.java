package com.crm.controller;

import com.crm.entity.User;
import com.crm.password.PasswordRequestUtil;
import com.crm.password.PasswordResetVerificationEmail;
import com.crm.service.UserService;
import com.crm.service.PasswordResetTokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final PasswordResetVerificationEmail eventListener;

    @Autowired
    public UserController(UserService userService, PasswordResetTokenService passwordResetTokenService, BCryptPasswordEncoder passwordEncoder, PasswordResetVerificationEmail eventListener) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
        this.eventListener = eventListener;
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userService.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User theUser) {
        try {
            String plainPassword = theUser.getPassword();
            String encodedPassword = passwordEncoder.encode(plainPassword);
            theUser.setPassword(encodedPassword);

            userService.save(theUser);

            return ResponseEntity.ok("User saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving user: " + e.getMessage());
        }
    }
    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException{
        Optional<User> user = userService.findByEmail(passwordRequestUtil.getEmail());
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);
        }
        return passwordResetUrl;

    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/auth/reset-password?token="+ passwordResetToken;
        eventListener.sendPasswordResetVerificationEmail(url);
        log.info("Click the link to reset your password :  {}", url);

        return url;
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                @RequestParam("token") String token){
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "Invalid token password reset token";
        }
        Optional<User> theUserOptional = userService.findUserByPasswordToken(token);
        if (theUserOptional.isPresent()) {
            User theUser = theUserOptional.get();
            userService.changePassword(theUser, passwordRequestUtil.getNewPassword());
            return "Password has been reset successfully";
        } else {
            return "Invalid password reset token";
        }
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                +request.getServerPort()+request.getContextPath();
    }
}
