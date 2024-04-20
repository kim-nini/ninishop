package com.jy_dev.ninishop.user;


import com.jy_dev.ninishop.core.errors.Exception400;
import com.jy_dev.ninishop.core.errors.Exception401;
import com.jy_dev.ninishop.core.errors.Exception500;
import com.jy_dev.ninishop.core.security.AES256;
import com.jy_dev.ninishop.core.security.JwtTokenProvider;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor // final일 경우 dto를 받아와 final의 기본생성자를 대체하고 jpa가 매핑할 수 있게 해줌
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final AES256 aes256; // 암호화클래스

    @Transactional
    public User join(UserRequest request) { // 회원가입
        try{
            if(userJPARepository.existsByEmail(request.getEmail())){
                throw new Exception400("동일한 이메일이 존재합니다 : "+request.getEmail());
            }

            // 암호화
            String password = aes256.aesCBCEncode(request.getPassword());
//            String username = aes256.aesCBCEncode(request.getUsername()); // 이름은 제외
            String username = request.getUsername();
            String email = aes256.aesCBCEncode(request.getEmail());
            String address = aes256.aesCBCEncode(request.getAddress());
            String phoneNumber = aes256.aesCBCEncode(request.getPhoneNumber());
            User user = request.toUserEntity(password,username,email,address,phoneNumber); // 암호화 재구현

            userJPARepository.save(user);
        } catch (Exception e){
            e.printStackTrace();
//            throw new Exception500("failed to join");
        }

        return userJPARepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new Exception500("cannot find user : "+ request.getUsername()));
    }

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


}
