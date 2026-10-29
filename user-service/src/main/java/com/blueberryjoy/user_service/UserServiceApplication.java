package com.blueberryjoy.user_service;

import com.blueberryjoy.user_service.model.User;
import com.blueberryjoy.user_service.service.CognitoUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.blueberryjoy.user_service.config.CognitoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import com.blueberryjoy.user_service.service.UserService;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
        CognitoConfiguration cognitoConfiguration = context.getBean(CognitoConfiguration.class);
        //S3Client s3 = S3Client.builder()
        //.credentialsProvider(cognitoConfiguration.getAwsCredentialsProvider())
        //.region(cognitoConfiguration.getRegion())
        //.build();

        //ListBucketsResponse response = s3.listBuckets();
        //System.out.println("Buckets:");
        //response.buckets().forEach(bucket -> System.out.println(bucket.name()));


        //CognitoUserService cognitoUserService = context.getBean(CognitoUserService.class);
        //User user = cognitoUserService.create(new User("id1", "userName2", "Password1#", "watercyb@hotmail.com", "+12065551212", null, null));

        //UserService userService=context.getBean(CognitoUserService.class);
        //AuthenticationResultType res = userService.login("cyb", "86230604Aa#");
        //System.out.println("Access token: " + res.accessToken());
        //System.out.println("ID token: " + res.idToken());
        //System.out.println("Refresh token: " + res.refreshToken());
    }

}
