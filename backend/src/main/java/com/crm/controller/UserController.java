package com.crm.controller;

import com.crm.controller.dto.UserDto;
import com.crm.entity.Role;
import com.crm.entity.User;
import com.crm.password.PasswordRequestUtil;
import com.crm.service.PasswordResetTokenService;
import com.crm.service.RoleService;
import com.crm.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, PasswordResetTokenService passwordResetTokenService, RoleService roleService, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper  = modelMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        Optional<User> userOptional = userService.findByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No active session", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
            String plainPassword = user.getPassword();
            String encodedPassword = passwordEncoder.encode(plainPassword);
            user.setPassword(encodedPassword);

            Role role = roleService.findById(user)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);

            userService.save(user);

            return ResponseEntity.ok("User saved successfully");

    }
    @PostMapping("/password-reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                       final HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException{
        Optional<User> user = userService.findByEmail(passwordRequestUtil.getEmail());
        System.out.println(user);
        String passwordResetUrl = "";
        if (user.isPresent()) {
            String passwordResetToken = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(servletRequest), passwordResetToken);

            return ResponseEntity.ok("Your token " + passwordResetUrl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that email not found");

    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken)
            throws MessagingException, UnsupportedEncodingException {
        String url = applicationUrl+"/api/auth/reset-password?token="+ passwordResetToken;

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


    @GetMapping("/get-users")
    public ResponseEntity<?> findAllUsers() {
        List<User> listOfUsers = userService.findAllUsers();

        if (!listOfUsers.isEmpty()) {
            List<UserDto> userDtos = listOfUsers.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no users in the database", HttpStatus.NOT_FOUND);
        }
    }
}
