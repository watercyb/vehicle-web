package com.blueberryjoy.gateway.proxy;

import com.blueberryjoy.gateway.model.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Data
@Component
public class UserProxy {

    public final RestTemplate restTemplate;
    private final String url;

    public UserProxy(@Value("${url.user}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public User create(User user) {
        return restTemplate.postForObject(url + "users", user, User.class);
    }

    public User[] getAllUsers() {
        return restTemplate.getForObject(url + "users", User[].class);
    }

    public User getUser(String userId) {
        return restTemplate.getForObject(url + "users/{userId}", User.class, Map.of("userId", userId));
    }
}
