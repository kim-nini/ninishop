package org.example.userservice.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MyPage {
        private String email;
        private String username;
        private String password;
        private String address;
        private String phoneNumber;

        @Builder
        public MyPage(String email, String username, String password, String address, String phoneNumber) {
            this.email = email;
            this.username = username;
            this.password = password;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }
    }



}
