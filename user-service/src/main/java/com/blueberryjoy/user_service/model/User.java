package com.blueberryjoy.user_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String id;

    @NotBlank(message = "Username is required")
    private String login;

    @Size(min = 6, message = "Password nust be at least 6 characters")
    private String password;
    private String email;
    private String phone;

    private Profile profile;

    private Date created;
    private Date modified;

    public void setProfile(String profile) {
        try {
            this.profile = Profile.valueOf(profile);
        } catch (Exception e) {
            this.profile = Profile.USER;
        }
    }
}
