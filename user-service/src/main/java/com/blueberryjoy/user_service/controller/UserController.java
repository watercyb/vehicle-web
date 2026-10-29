package com.blueberryjoy.user_service.controller;

import com.blueberryjoy.user_service.model.User;
import com.blueberryjoy.user_service.model.Login;
import com.blueberryjoy.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import java.util.List;

@RestController
@RequestMapping({"/users", "/users/"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{userId}")
    public User getUsers(@PathVariable String userId) {
        return userService.getUsers(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User createdUser = userService.create(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //test
    @PostMapping({"/login", "/login/"})
    public ResponseEntity<Login> login(@Valid @RequestBody Login login) {
        AuthenticationResultType res = userService.login(login.getUsername(), login.getPassword());
        System.out.println("Access token: " + res.accessToken());
        System.out.println("ID token: " + res.idToken());
        System.out.println("Refresh token: " + res.refreshToken());
        return new ResponseEntity<>(login, HttpStatus.CREATED);
    }
}
