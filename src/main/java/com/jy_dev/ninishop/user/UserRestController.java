package com.jy_dev.ninishop.user;

import com.jy_dev.ninishop.core.errors.Exception400;
import com.jy_dev.ninishop.core.errors.Exception500;
import com.jy_dev.ninishop.core.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    // (기능1) 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest request){
        User user = userService.join(request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(user);
        return ResponseEntity.ok(apiResult);
    }

    // (기능2) 로그인
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest request){
         try {
             String jwt = userService.login(request);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         return  null;
     }

    // 이메일 검사
    // @PostMapping("/check")

    // (기능3) - 로그아웃
    // @GetMapping("/logout")

}