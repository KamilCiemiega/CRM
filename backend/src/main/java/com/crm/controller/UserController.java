package com.crm.controller;

import com.crm.controller.dto.NewUserDTO;
import com.crm.controller.dto.UserDTO;
import com.crm.entity.User;
import com.crm.service.PasswordResetTokenService;
import com.crm.service.UserService;
import com.crm.utils.PasswordRequestUtil;
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
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }//returning eventgit

    @PostMapping()
    public ResponseEntity<UserDTO> saveUser(@RequestBody NewUserDTO newUserDTO) {
        return new ResponseEntity<>(userService.save(newUserDTO), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/{user-id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("user-id") int userId, @RequestBody NewUserDTO newUserDTO){
        return new ResponseEntity<>(userService.updateUser(userId, newUserDTO), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody NewUserDTO newUserDTO, HttpServletRequest request) {
            return new ResponseEntity<>(userService.login(newUserDTO, request), HttpStatus.OK);
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

    @PostMapping("/password-reset-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                                       final HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {
        String passwordResetUrl = userService.createPasswordResetRequest(passwordRequestUtil.getEmail(), applicationUrl(servletRequest));
        return passwordResetUrl != null
                ? ResponseEntity.ok("Your token " + passwordResetUrl)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with that email not found");
    }

    private String passwordResetEmailLink(User user, String applicationUrl, String passwordResetToken)
            throws MessagingException, UnsupportedEncodingException {
        return applicationUrl+"/api/auth/reset-password?token="+ passwordResetToken;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordRequestUtil passwordRequestUtil,
                                                @RequestParam("token") String token) {
        String result = userService.resetPassword(passwordRequestUtil.getNewPassword(), token);
        return ResponseEntity.ok(result);
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
