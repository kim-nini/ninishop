package org.example.userservice.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank // null, "", " " 모두 허용안함
    private String username;

    @NotBlank
    @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요.")
    private String email;

    @NotBlank
//    @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

//    @NotBlank
//    private String authNum;

    public User toUserEntity(String password, String username, String email, String address, String phoneNumber,  List<String> roles){
        return User.builder()
                .username(username)
                .email(email)
                .roles(roles)
                .address(address)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
    }

    public UserRequest(String username, String password, String email, String address, String phoneNumber){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address =address;
    }

    @Getter
    public static class EmailCheck {
        private String email;
        private String authNum;
    }

}
