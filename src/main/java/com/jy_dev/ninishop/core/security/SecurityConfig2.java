//package com.jy_dev.ninishop.core.security;
//
//import exception.errors.core.org.example.orderservice.Exception401;
//import exception.errors.core.org.example.orderservice.Exception403;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
//        @Override
//        public void configure(HttpSecurity builder) throws Exception {
//            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
//            builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
//            super.configure(builder);
//        }
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 1. CSRF 해제
//        http.csrf(AbstractHttpConfigurer::disable); // postman 접근해야 함!! - CSR 할때!!
////
////        // 2. iframe 거부
////        http.headers().frameOptions().sameOrigin();
////
////        // 3. cors 재설정
////        http.cors().configurationSource(configurationSource());
////
////        // 4. jSessionId 사용 거부 (5번을 설정하면 jsessionId가 거부되기 때문에 4번은 사실 필요 없다)
//////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.sessionManagement((sessionManagement) ->
////                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        );
////
////        // 5. form 로긴 해제 (UsernamePasswordAuthenticationFilter 비활성화)
////        http.formLogin(AbstractHttpConfigurer::disable);
////
////        // 6. 로그인 인증창이 뜨지 않게 비활성화
////        http.httpBasic(AbstractHttpConfigurer::disable);
//////        http.httpBasic().disable();
////
////        // 7. 커스텀 필터 적용 (시큐리티 필터 교환)
////        http.apply(new CustomSecurityFilterManager());
////
////        // 8. 인증 실패 처리
////        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
////            log.warn("인증되지 않은 사용자가 자원에 접근하려 합니다 : "+authException.getMessage());
////            FilterResponseUtils.unAuthorized(response, new Exception401("인증되지 않았습니다"));
////        });
////
////        // 9. 권한 실패 처리
////        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
////            log.warn("권한이 없는 사용자가 자원에 접근하려 합니다 : "+accessDeniedException.getMessage());
////            FilterResponseUtils.forbidden(response, new Exception403("권한이 없습니다"));
////        });
////
////        // 11. 인증, 권한 필터 설정
////        http.authorizeRequests(
////                authorize -> authorize.antMatchers("/carts/**", "/options/**", "/orders/**").authenticated()
////                        .antMatchers("/admin/**")
////                        .access("hasRole('ADMIN')")
////                        .anyRequest().permitAll()
////        );
//
//        return http.build();
//    }
//
//    public CorsConfigurationSource configurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE
//        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용
//        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
//        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트. 지금은 아님
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
