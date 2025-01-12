package com.crm.controller;

import com.crm.controller.dto.user.NewUserDTO;
import com.crm.controller.dto.user.UserDTO;
import com.crm.entity.User;
import com.crm.service.UserService;
import com.crm.utils.PasswordRequestUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        List<UserDTO> allUsersDTO = allUsers.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();

        return new ResponseEntity<>(allUsersDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> saveUser(@RequestBody NewUserDTO newUserDTO) {
        User user = modelMapper.map(newUserDTO, User.class);
        User savedUser = userService.save(user);
        UserDTO userDTO = modelMapper.map(savedUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{user-id}")
    @Transactional
    public ResponseEntity<UserDTO> updateUser(@PathVariable("user-id") int userId, @RequestBody NewUserDTO newUserDTO) {
        User userToUpdate = modelMapper.map(newUserDTO, User.class);
        User updatedUser = userService.updateUser(userId, userToUpdate);
        UserDTO userDTO = modelMapper.map(updatedUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody NewUserDTO newUserDTO, HttpServletRequest request) {
            User user = modelMapper.map(newUserDTO, User.class);
            UserDTO userDTO = modelMapper.map(userService.login(user, request), UserDTO.class);

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
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
