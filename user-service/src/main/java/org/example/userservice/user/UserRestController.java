package org.example.userservice.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.core.errors.exception.Exception400;
import org.example.userservice.core.security.JwtTokenProvider;
import org.example.userservice.core.security.UserDetails;
import org.example.userservice.core.security.UserInfo;
import org.example.userservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;
    private final MailService mailService;

    // 이메일인증 먼저한 후에 회원가입
    // 이메일인증
    @PostMapping("/send-mail")
    public String mailSend(@RequestBody @Valid UserRequest.EmailCheck emailCheck) {
        System.out.println("이메일 인증 요청이 들어옴");
        System.out.println("이메일 인증 이메일 :" + emailCheck.getEmail());
        return mailService.joinEmail(emailCheck.getEmail());
    }

    @PostMapping("/auth-check") // 인증번호 확인
    public String AuthCheck(@RequestBody @Valid UserRequest.EmailCheck emailCheck) {
        boolean Checked = mailService.CheckAuthNum(emailCheck.getEmail(), emailCheck.getAuthNum());
        if (Checked) {
            return "ok";
        } else {
            throw new Exception400("인증번호가 잘못되었습니다.");
        }
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest request) {
        userService.join(request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(null);
        return ResponseEntity.ok(apiResult);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        Token jwt = userService.login(request);
        return ResponseEntity.ok().header(JwtTokenProvider.HEADER, jwt.getAccessToken()).body(ApiUtils.success(null));
    }

    // (기능3) - 로그아웃
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(){
//        return null;
//    }

    // 마이페이지
    @GetMapping("/info")
    public ResponseEntity<?> myInfo(@UserInfo UserDetails userDetails) {
        UserResponse.MyPage userInfo = userService.getUserInfo(userDetails.getUserId());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(userInfo);
        return ResponseEntity.ok(apiResult);
    }

    // 정보 수정
    @PostMapping("/info/update")
    public ResponseEntity<?> updateInfo(@RequestBody UserRequest request) {
        UserResponse.MyPage updateCompletedInfo = userService.update(request);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(updateCompletedInfo);
        return ResponseEntity.ok(apiResult);
    }

}