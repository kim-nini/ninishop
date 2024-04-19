package com.jy_dev.ninishop.user;

import lombok.Getter;
import lombok.Setter;


public class UserRequest {

    @Getter
    @Setter
    public static class LoginDTO {
        private String email;
        private String password;

    }

    @Getter
    @Setter
    public static class JoinDTO {
        private String email;
        private String password;
        private String username;
    }

    @Getter
    @Setter
    public static class EmailCheckDTO {
        private String email;
    }
}
