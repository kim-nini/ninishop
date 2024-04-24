//package com.jy_dev.ninishop.core.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class SecurityConfig3 { // 클럽가드같은거 ~
//
//    //
//    // 인증을 해서 인증된 사용자만 접근할수있게 해주면 매번 확인하거나 매번 인증할 필요가 없음
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean // 세션설정 세션
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    // 유저의 정보가 맞는지 확인하는게 AuthenticationManager
//    // SECURITY CONTEXT HOLDER (지갑같은거)
//    // security context : 민증같은거
//    // authentication : principal - 사용자/사용자객체(userDetails), credentials - 비밀번호(주민번호뒷자리같은거), authorities : 사용자에게 부여한 권한 user,admin
//    // 만약에 로그인 실패 했을 경우 익셉션을 던짐  ex) /not-authorized 로 엔트리포인트 만들어서 권한이없읍니다 이런거 나오게
//    // 컨트롤러 오기전까지 다 체크해서 걸러버림
//
//    // websecurityCostomizer  : 얘가 내 db를 사용 하게 할 건지
//    // (web) -> web.ignoring() : 이경로로 들어오면 db사용할 수 있으니까 인증필요없이 통과
//    // .requestMatchers(PathRequest.toH2Console()) : 로그인창
//    //
//
//    // CustomSecurityFilter 밑에 필터체인을 빈으로 등록해서 할수도있찌만 내가 직접만들어서 할 수 도있음
//    // 지금은 사용 안하는 부분
////    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
////        @Override
////        public void configure(HttpSecurity builder) throws Exception {
////            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
////            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
////            super.configure(builder);
////        }
////    }
//
//
//    // 컨트롤러전에 민증검사 할지말지 결정하는 filter임
//    // http 요청이들어오면
//    @Bean // 필터를 하나씩 넣어서 이거 안되면 제외 제외 제외 이런식 근데 여러개잇어서 필터체인이됨
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        AuthenticationManager authenticationManager = authenticationManager(new AuthenticationConfiguration());
//
//
//        log.debug("[+] WebSecurityConfig Start !!! ");
//        //crsf disable : 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
//        http.csrf(c -> c.disable());
//        // 스프링에서 제공하는 로그인 폼 사용할지 말지 : 사용안함 disable
//        http.formLogin(f -> f.disable());
//        //http basic 인증 방식 disable
//        http.httpBasic(hb -> hb.disable());
//
//        http.headers(auth -> auth.disable());
//
//        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 클래스 전에 삽입
//        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
//
//        //경로별 인가작업
//        // 인증이 필요한 url에 대해서 .antMatchers(/css/**") 검사를 할지 말지 설정하는거 .permitAll() : 검사안하고 허가하는거
//        // requestMatchers
//        // .anyRequest().authenticated(); 여기써놓은 요청말고는 전부 다 검사하라고 하는거 => .fullyAuthenticated() : 써놓은거 뺴고 다 검사하라고하는거
//        // addfilterBefore(new JwtAuthfilter(jwtutil,tokenService) -> 토큰 만료등 확인하는거에 던지는거임 (필터하나 더 추가하는거 )
//
//        // 로그인 실패했을때 로그인페이지로 보내는거. 로그아웃햇을때 로그인페이지로 보내는거 그런거 다 설정할 수 있음
//        http.authorizeHttpRequests((auth) -> auth
//                .anyRequest().permitAll());
//
//        //세션 설정 : 세션을 안쓰고 jwt를 쓸거니까 stateless 방식으로 설정
//        // jwt 인증만 확인하겠다
//        http.sessionManagement((session) -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
////        http
////                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
////                        UsernamePasswordAuthenticationFilter.class);
////
////        http
////                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
//
//
//        return http.build();
//    }
//
////    public CorsConfigurationSource configurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.addAllowedHeader("*");
////        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE
////        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
////        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
////        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트. 지금은 아님
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//}
