package org.example.userservice.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.core.errors.exception.Exception400;
import org.example.userservice.core.security.JwtTokenProvider;
import org.example.userservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final MailService mailService;

    // (기능1) 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest request) {
        User user = userService.join(request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(user);
        return ResponseEntity.ok(apiResult);
    }

    // 이메일인증 먼저한 후에 회원가입
    // (기능1-1) 이메일인증
    @PostMapping("/send-mail")
    public String mailSend(@RequestBody @Valid UserRequest.EmailCheck emailCheck) {
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :" + emailCheck.getEmail());
        return mailService.joinEmail(emailCheck.getEmail());
    }

    @PostMapping("/auth-check") // 인증번호 확인
    public String AuthCheck(@RequestBody @Valid UserRequest.EmailCheck emailCheck) {
        Boolean Checked = mailService.CheckAuthNum(emailCheck.getEmail(), emailCheck.getAuthNum());
        if (Checked) {
            return "ok";
        } else {
            throw new Exception400("인증번호가 잘못되었습니다.");
        }
    }


    // (기능2) 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        Token jwt = userService.login(request);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt.getAccessToken()).body(ApiUtils.success(null));
//        return ResponseEntity.ok().body(ApiUtils.success(jwt));
    }

    // (기능3) - 로그아웃
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(){
//        return null;
//    }

    // (기능4) - 수정
    @PostMapping("/update-info")
    public ResponseEntity<?> updateInfo(@RequestBody UserRequest request, Authentication authentication) {
//        @RequestBody UserRequest request,
        User user = userService.update(request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(user);
        return ResponseEntity.ok(apiResult);
    }

}