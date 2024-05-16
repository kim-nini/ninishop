package org.example.userservice.user;


import lombok.RequiredArgsConstructor;
import org.example.userservice.core.errors.exception.Exception401;
import org.example.userservice.core.errors.exception.Exception500;
import org.example.userservice.core.security.AES256;
import org.example.userservice.core.security.JwtTokenProvider;
import org.example.userservice.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor // final일 경우 dto를 받아와 final의 기본생성자를 대체하고 jpa가 매핑할 수 있게 해줌
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;
    private final AES256 aes256; // 암호화클래스
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final MailService mailService;
    private final RedisService redisService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Transactional
    public User join(UserRequest request) { // 회원가입
        try {
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            // 암호화
            User user = User.builder()
                    .password(passwordEncoder.encode(request.getPassword()))
                    .username(request.getUsername())
                    .email(aes256.aesCBCEncode(request.getEmail()))
                    .address(aes256.aesCBCEncode(request.getAddress()))
                    .phoneNumber(aes256.aesCBCEncode(request.getPhoneNumber()))
                    .roles(roles)
                    .build();

            userJPARepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new Exception500("cannot find user : " + request.getUsername()));
    }

    @Transactional
    public Token login(UserRequest request) { // 로그인
        User user = userJPARepository.findByEmail(aes256.aesCBCEncode(request.getEmail()))
                .orElseThrow(() -> new Exception500("cannot find email : " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception401("wrong email or password");
        }
          String jwt = JwtTokenProvider.createRefreshToken();
        return new Token(JwtTokenProvider.createAccessToken(user),JwtTokenProvider.createRefreshToken());
    }

    @Transactional
    public User update(UserRequest request) { // 수정
        try {
            User user = userJPARepository.findByEmail(aes256.aesCBCEncode(request.getEmail()))
                    .orElseThrow(() -> new Exception500("cannot find user : " + request.getEmail()));

            String password = passwordEncoder.encode(request.getPassword());
            user.updateInfo(password, aes256.aesCBCEncode(request.getAddress()), aes256.aesCBCEncode(request.getPhoneNumber()));

            userJPARepository.save(user);
        } catch (Exception e) {
            throw new Exception500("failed to update");
        }
        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new Exception500("cannot find user : " + request.getUsername()));
    }

    public UserResponse.Mypage getUserInfo(String stringUserId)  {
        User user = userJPARepository.findById(Long.parseLong(stringUserId)).orElseThrow(() -> new Exception500("cannot find user : " + stringUserId));
        UserResponse.Mypage mypage = null;
        try {
            mypage = UserResponse.Mypage.builder()
                    .username( aes256.aesCBCDecode(user.getUsername()))
                    .email(aes256.aesCBCDecode(user.getEmail()))
                    .phoneNumber(aes256.aesCBCDecode(user.getPhoneNumber()))
                    .address(aes256.aesCBCDecode(user.getAddress()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mypage;
    }


}
