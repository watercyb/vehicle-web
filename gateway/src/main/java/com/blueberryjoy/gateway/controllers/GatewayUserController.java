package com.blueberryjoy.gateway.controllers;

import com.blueberryjoy.gateway.model.User;
import com.blueberryjoy.gateway.proxy.UserProxy;
import com.blueberryjoy.gateway.util.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@AllArgsConstructor
public class GatewayUserController {

    private final UserProxy userProxy;
    private final ObjectFactory<UserContext> userContext;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        userContext.getObject().isAdmin();
        return new ResponseEntity<>(userProxy.create(user), HttpStatus.CREATED);
    }

    @GetMapping
    public User[] getAllUsers() {
        return userProxy.getAllUsers();
    }

    @GetMapping("{userId}")
    public User getUsers(@PathVariable String userId) {
        return userProxy.getUser(userId);
    }
}
