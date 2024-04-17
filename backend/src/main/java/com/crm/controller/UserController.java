package com.crm.controller;

import com.crm.entity.User;
import com.crm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(CsrfToken token) {
//        return token;
//    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User theUser) {
        userService.save(theUser);
        return ResponseEntity.ok("User saved successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
