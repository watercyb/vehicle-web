package com.blueberryjoy.user_service.service;

import com.blueberryjoy.user_service.config.CognitoConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import com.blueberryjoy.user_service.model.User;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserBuilderHelper {
    private static final String ID = "sub";
    private static final String EMAIL = "email";
    private static final String PROFILE = "profile";
    private static final String EMAIL_VERIFIED = "email_verified";
    private static final String PHONE = "phone_number";
    private static final String PHONE_VERIFIED = "phone_number_verified";

    private final CognitoConfiguration configuration;

    public AdminCreateUserRequest adminCreateUserRequest(User user) {
        return AdminCreateUserRequest.builder()
                .userPoolId(configuration.getPoolId())
                .username(user.getLogin())
                .messageAction(MessageActionType.SUPPRESS)
                .userAttributes(
                        AttributeType.builder().name(EMAIL).value(user.getEmail()).build(),
                        AttributeType.builder().name(EMAIL_VERIFIED).value("true").build(),
                        AttributeType.builder().name(PHONE).value(user.getPhone()).build(),
                        AttributeType.builder().name(PHONE_VERIFIED).value("true").build(),
                        AttributeType.builder().name(PROFILE).value(user.getProfile().name()).build()
                ).build();
    }

    //Translate response from Cognito (UserType) to User type
    public User build(UserType userType) {
        var user = new User();
        user.setLogin(userType.username());

        Map<String, String> map = userType.attributes()
                .stream()
                .collect(Collectors.toMap(AttributeType::name, AttributeType::value));

        user.setId((map.get(ID)));
        user.setEmail((map.get(EMAIL)));
        user.setPhone((map.get(PHONE)));
        user.setProfile((map.get(PROFILE)));

        user.setCreated(Date.from(userType.userCreateDate()));
        user.setModified(Date.from(userType.userLastModifiedDate()));

        return user;
    }
}
