package com.jy_dev.ninishop.core.security;

import com.jy_dev.ninishop.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    // 권한 role 뭔지까지 넣어주는거
    // 이걸 걸어주면 컨트롤러에서 @secured 어노테이션으로 권한설정가는
    // @Secured("ROLE_ADMIN") 이렇게 쓰면 됨 이걸 쓰고싶으면 securitiyConfig에 어노테이션걸어서 secured사용설정 하면됨
    // @EnableGlobalMethodSecurity(securedEnabled = true)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
