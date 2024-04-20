package com.jy_dev.ninishop.user;

import com.jy_dev.ninishop.core.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // (기능1-1) 이메일인증
//    @PostMapping("/emails/verification-requests")
//    public ResponseEntity sendMessage(@RequestParam("email") @Valid @CustomEmail String email) {
//        memberService.sendCodeToEmail(email);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/emails/verifications")
//    public ResponseEntity verificationEmail(@RequestParam("email") @Valid @CustomEmail String email,
//                                            @RequestParam("code") String authCode) {
//        EmailVerificationResult response = memberService.verifiedCode(email, authCode);
//
//        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
//    }

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