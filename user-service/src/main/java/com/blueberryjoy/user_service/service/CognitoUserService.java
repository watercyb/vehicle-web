package com.blueberryjoy.user_service.service;

import com.blueberryjoy.user_service.config.CognitoConfiguration;
import com.blueberryjoy.user_service.exceptions.UserNotFoundException;
import com.blueberryjoy.user_service.model.User;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.*;

@Service
public class CognitoUserService implements UserService {
    private final CognitoConfiguration configuration;
    private final UserBuilderHelper userBuilderHelper;
    private final CognitoIdentityProviderClient provider;

    public CognitoUserService(CognitoConfiguration configuration, UserBuilderHelper userBuilderHelper) {
        this.configuration = configuration;
        this.userBuilderHelper = userBuilderHelper;
        this.provider = createProvider(configuration);
    }

    private CognitoIdentityProviderClient createProvider(CognitoConfiguration configuration) {
        return CognitoIdentityProviderClient.builder().credentialsProvider(configuration.getAwsCredentialsProvider()).region(configuration.getRegion()).build();
    }

    @Override
    public User create(User user) {
        User createdUser = createDefaultPasswordUser(user);
        updateWithPermanentPassword(user.getLogin(), user.getPassword());
        return createdUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String paginationToken = null;
        do {
            var requestBuilder = ListUsersRequest.builder().userPoolId(configuration.getPoolId()).limit(60);
            if (paginationToken != null) requestBuilder.paginationToken((paginationToken));
            var response = provider.listUsers(requestBuilder.build());
            response.users().stream().map(userBuilderHelper::build).forEach(allUsers::add);
            paginationToken = response.paginationToken();
        } while (paginationToken != null);
        return allUsers;
    }

    @Override
    public User getUsers(String userId) {
        var request = ListUsersRequest.builder().userPoolId(configuration.getPoolId()).filter(String.format("sub = \"%s\"", userId)).build();

        var response = provider.listUsers(request);
        return response.users().stream().findFirst().map(userBuilderHelper::build).orElseThrow(() -> new UserNotFoundException(userId));
    }

    private void updateWithPermanentPassword(String userName, String password) {
        var passwordRequest = AdminSetUserPasswordRequest.builder().username(userName).password(password).permanent(true).userPoolId(configuration.getPoolId()).build();

        provider.adminSetUserPassword(passwordRequest);
    }

    private User createDefaultPasswordUser(User user) {
        return Optional.of(user).map(userBuilderHelper::adminCreateUserRequest).map(provider::adminCreateUser).map(AdminCreateUserResponse::user).map(userBuilderHelper::build).orElseThrow();
    }


    //test
    @Override
    public AuthenticationResultType login(String username, String password) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);

        InitiateAuthRequest authRequest = InitiateAuthRequest.builder().authFlow(AuthFlowType.USER_PASSWORD_AUTH).clientId(configuration.getClientId()).authParameters(authParams).build();

        InitiateAuthResponse response = provider.initiateAuth(authRequest);
        return response.authenticationResult();
    }
}
