package com.ideawithtrade.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ideawithtrade.server.model.User;
import com.ideawithtrade.server.Service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        boolean isAuthenticated = userService.authenticate(email, password);
        Map<String, Object> response = new HashMap<>();

        if (isAuthenticated) {
            response.put("message", "Login successful");
            response.put("result", 1);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid email or password");
            response.put("result", 2);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setCompany(user.getCompany());
        newUser.setEmail(user.getEmail());

        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());

        return userService.save(newUser);
    }
}