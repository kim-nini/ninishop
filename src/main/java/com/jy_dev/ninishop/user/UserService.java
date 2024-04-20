package com.jy_dev.ninishop.user;


import com.jy_dev.ninishop.core.errors.Exception400;
import com.jy_dev.ninishop.core.errors.Exception401;
import com.jy_dev.ninishop.core.errors.Exception500;
import com.jy_dev.ninishop.core.security.AES256;
import com.jy_dev.ninishop.core.security.JwtTokenProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional(readOnly = true)
@RequiredArgsConstructor // final일 경우 dto를 받아와 final의 기본생성자를 대체하고 jpa가 매핑할 수 있게 해줌
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;
    private final AES256 aes256; // 암호화클래스
//    private static final String AUTH_CODE_PREFIX = "AuthCode ";
//    private final MailService mailService;
////    private final RedisService redisService;
//    @Value("${spring.mail.auth-code-expiration-millis}")
//    private long authCodeExpirationMillis;

    @Transactional
    public User join(UserRequest request) { // 회원가입
        try{
            if(userJPARepository.existsByEmail(request.getEmail())){
                throw new Exception400("동일한 이메일이 존재합니다 : "+request.getEmail());
            }

            // 암호화
            User user = User.builder()
                    .password(passwordEncoder.encode(request.getPassword()))
                    .username(request.getUsername())
                    .email(aes256.aesCBCEncode(request.getEmail()))
                    .address(aes256.aesCBCEncode(request.getAddress()))
                    .phoneNumber(aes256.aesCBCEncode(request.getPhoneNumber()))
                    .build();

            userJPARepository.save(user);
        } catch (Exception e){
            e.printStackTrace();
//            throw new Exception500("failed to join");
        }

        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new Exception500("cannot find user : "+ request.getUsername()));
    }

//    public void sendCodeToEmail(String toEmail) { // 이메일 보내기
//        this.checkDuplicatedEmail(toEmail);
//        String title = "Travel with me 이메일 인증 번호";
//        String authCode = this.createCode();
//        mailService.sendEmail(toEmail, title, authCode);
//        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
////        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
////                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
//    }
//
//    private void checkDuplicatedEmail(String email) {
////        Optional<Member> member = memberRepository.findByEmail(email);
////        if (member.isPresent()) {
////            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
////            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
////        }
//    }
//
//    private String createCode() {
//        int lenth = 6;
//        try {
//            Random random = SecureRandom.getInstanceStrong();
//            StringBuilder builder = new StringBuilder();
//            for (int i = 0; i < lenth; i++) {
//                builder.append(random.nextInt(10));
//            }
//            return builder.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            throw new Exception500("이메일 인증 코드 생성 에러");
////            log.debug("MemberService.createCode() exception occur");
////            throw new BusinessLogicException(ExceptionCode.NO_SUCH_ALGORITHM);
//        }
//    }

//    public EmailVerificationResult verifiedCode(String email, String authCode) {
//        this.checkDuplicatedEmail(email);
//        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
//        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);
//
//        return EmailVerificationResult.of(authResult);
//    }




/*****************************************************************************************************/


    @Transactional
    public String login(UserRequest request) { // 로그인
        User user = userJPARepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception500("cannot find email : "+request.getEmail()));

        try {
            if (aes256.aesCBCEncode(request.getPassword()).equals(user.getPassword()) ) {
                throw new Exception401("wrong email or password");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String token = JwtTokenProvider.create(user);

        return token;
    }

    @Transactional
    public User update(UserRequest request) { // 수정
        try {

            User user = userJPARepository.findByEmail(request.getEmail())
                    .orElseThrow(()-> new Exception500("cannot find user : "+ request.getEmail()));

            String password = passwordEncoder.encode(request.getPassword());

            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");

            user.updateInfo(password, request.getUsername(), roles);

            userJPARepository.save(user);
        } catch (Exception e) {
            throw new Exception500("failed to update");
        }
        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new Exception500("cannot find user : "+ request.getUsername()));
    }

}
