package com.blueberryjoy.gateway.util;


import com.blueberryjoy.gateway.exceptions.UserUnauthorizedException;
import com.blueberryjoy.gateway.model.Profile;
import com.blueberryjoy.gateway.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Data
@RequestScope
public class UserContext {

    private User user;

    public void isAdmin() {
        if (user==null||!Profile.ADMIN.equals(user.getProfile()))
            throw new UserUnauthorizedException();
    }
}
