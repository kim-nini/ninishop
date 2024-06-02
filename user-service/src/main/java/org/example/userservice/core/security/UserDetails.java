package org.example.userservice.core.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetails {
    private String userId;

    // 생성자
    public UserDetails(String userId) {
        this.userId = userId;
    }

}
