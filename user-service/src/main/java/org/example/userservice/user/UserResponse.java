package org.example.userservice.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Mypage {
        private String email;
        private String username;
        private String address;
        private String phoneNumber;

        @Builder
        public Mypage(String email, String username, String address, String phoneNumber) {
            this.email = email;
            this.username = username;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }
    }



}
