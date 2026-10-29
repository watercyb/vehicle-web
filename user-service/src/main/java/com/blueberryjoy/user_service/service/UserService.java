package com.blueberryjoy.user_service.service;

import com.blueberryjoy.user_service.model.User;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;

import java.util.List;

public interface UserService {
    User create(User user);

    List<User> getAllUsers();

    User getUsers(String userId);

    //test
    AuthenticationResultType login(String username, String password);
}
