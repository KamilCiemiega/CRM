package com.crm.controller;

import com.crm.entity.User;
import com.crm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(CsrfToken token) {
//        return token;
//    }

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
    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User theUser) {
        authService.save(theUser);
        return ResponseEntity.ok("User saved successfully");
    }



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional <User> userOptional = authService.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
