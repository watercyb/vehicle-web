package com.blueberryjoy.user_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;


@Data
@Configuration
@ConfigurationProperties(prefix = "cognito.client")
public class CognitoConfiguration {

    // access key ID
    private String key;
    // secret access key
    private String secret;
    private String region;
    private String poolId;
    //test
    private String clientId;
    private AwsCredentialsProvider awsCredentialsProvider;

    public Region getRegion() {
        return Region.of(region);
    }

    public AwsBasicCredentials getCredentials() {
        return AwsBasicCredentials.create(key, secret);
    }

    public AwsCredentialsProvider getCredentialsProvider() {
        return StaticCredentialsProvider.create(getCredentials());
    }

    public AwsCredentialsProvider getAwsCredentialsProvider() {
        if (awsCredentialsProvider == null) awsCredentialsProvider = getCredentialsProvider();
        return awsCredentialsProvider;
    }
}
