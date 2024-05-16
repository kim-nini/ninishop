package org.example.userservice.core.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    // 비밀번호 암호화 빈 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean // 인증 매니저 빈 설정
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 컨트롤러전에 민증검사 할지말지 결정하는 filter임
    // http 요청이들어오면
    @Bean // 필터를 하나씩 넣어서 이거 안되면 제외 제외 제외 이런식 근데 여러개잇어서 필터체인이됨
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(new AuthenticationConfiguration());

        log.debug("[+] WebSecurityConfig Start !!! ");
        //crsf disable : 서버에 인증정보를 저장하지 않기에 csrf를 사용하지 않는다.
        http.csrf(AbstractHttpConfigurer::disable);
        // 스프링에서 제공하는 로그인 폼 사용할지 말지 : 사용안함 disable
        http.formLogin(AbstractHttpConfigurer::disable);
        //http basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.headers(AbstractHttpConfigurer::disable);

        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 클래스 전에 삽입
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        // 로그인 실패했을때 로그인페이지로 보내는거. 로그아웃햇을때 로그인페이지로 보내는거 그런거 다 설정할 수 있음
        //
        http.authorizeHttpRequests((auth) -> auth
                .anyRequest().permitAll());
//        http.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/login", "/api/goods", "/api/goods/**", "/sign-up").permitAll()
//                .requestMatchers("/my-page","/wishlist", "/logout", "api/orders").hasAuthority("customer")
//                .anyRequest().authenticated()); //경로별 인가 작업



        //세션 설정 : 세션을 안쓰고 jwt를 쓸거니까 stateless 방식으로 설정
        // jwt 인증만 확인하겠다
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
//                        UsernamePasswordAuthenticationFilter.class);
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


        return http.build();
    }

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
}
