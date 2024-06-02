package org.example.userservice.user;


import lombok.RequiredArgsConstructor;
import org.example.userservice.core.errors.exception.Exception401;
import org.example.userservice.core.errors.exception.Exception500;
import org.example.userservice.core.security.AES256;
import org.example.userservice.core.security.JwtTokenProvider;
import org.example.userservice.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor // final일 경우 dto를 받아와 final의 기본생성자를 대체하고 jpa가 매핑할 수 있게 해줌
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final AES256 aes256; // 암호화클래스
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final MailService mailService;
    private final RedisService redisService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Transactional
    public void join(UserRequest request) { // 회원가입
        User user = User.builder()
                .password(aes256.aesCBCEncode(request.getPassword()))
                .username(request.getUsername())
                .email(aes256.aesCBCEncode(request.getEmail()))
                .address(aes256.aesCBCEncode(request.getAddress()))
                .phoneNumber(aes256.aesCBCEncode(request.getPhoneNumber()))
                .build();

        userJPARepository.save(user);
    }

    @Transactional
    public Token login(UserRequest request) { // 로그인
        User user = userJPARepository.findByEmail(aes256.aesCBCEncode(request.getEmail()))
                .orElseThrow(() -> new Exception500("cannot find email : " + request.getEmail()));

        if (!aes256.matches(request.getPassword(), user.getPassword())) {
            throw new Exception401("wrong email or password");
        }
//        String jwt = JwtTokenProvider.createRefreshToken();
        return new Token(JwtTokenProvider.createAccessToken(user), JwtTokenProvider.createRefreshToken());
    }

    @Transactional
    public UserResponse.MyPage update(UserRequest request) { // 수정
        User user = userJPARepository.findByEmail(aes256.aesCBCEncode(request.getEmail()))
                .orElseThrow(() -> new Exception500("cannot find user : " + request.getEmail()));

        user.updateInfo(aes256.aesCBCEncode(request.getPassword()), aes256.aesCBCEncode(request.getAddress()), aes256.aesCBCEncode(request.getPhoneNumber()));
        userJPARepository.save(user);

        return UserResponse.MyPage.builder()
                .username(user.getUsername())
                .password(aes256.aesCBCDecode(user.getPassword()))
                .email(aes256.aesCBCDecode(user.getEmail()))
                .phoneNumber(aes256.aesCBCDecode(user.getPhoneNumber()))
                .address(aes256.aesCBCDecode(user.getAddress()))
                .build();
    }

    public UserResponse.MyPage getUserInfo(String stringUserId) { // 마이페이지
        User user = userJPARepository.findById(Long.parseLong(stringUserId)).orElseThrow(() -> new Exception500("cannot find user : " + stringUserId));

        return UserResponse.MyPage.builder()
                .username(user.getUsername())
                .password(aes256.aesCBCDecode(user.getPassword()))
                .email(aes256.aesCBCDecode(user.getEmail()))
                .phoneNumber(aes256.aesCBCDecode(user.getPhoneNumber()))
                .address(aes256.aesCBCDecode(user.getAddress()))
                .build();
    }

//    public User toUserEntity(String password, String username, String email, String address, String phoneNumber,  List<String> roles){
////        return User.builder()
////                .username(username)
////                .email(email)
////                .roles(roles)
////                .address(address)
////                .phoneNumber(phoneNumber)
////                .password(password)
////                .build();
////    }


}
