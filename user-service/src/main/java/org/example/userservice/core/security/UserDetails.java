package org.example.userservice.core.security;

public class UserDetails {
    private String userId;

    // 생성자, getter 및 setter
    public UserDetails(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
