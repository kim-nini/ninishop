package com.jy_dev.ninishop.core.security;

import com.jy_dev.ninishop.user.UserJPARepository;
import com.jy_dev.ninishop.core.errors.exception.Exception401;
import com.jy_dev.ninishop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJPARepository.findByEmail(username).orElseThrow(
                () -> new Exception401("인증되지 않았습니다")
        );
        return new CustomUserDetails(user); // 인증된 객체를 반환함
    }
}
