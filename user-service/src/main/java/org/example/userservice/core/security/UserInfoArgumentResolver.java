package org.example.userservice.core.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 컨트롤러에서 리퀘스트파람이나 패스배리어블처럼
@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserInfo.class) && parameter.getParameterType().equals(UserDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String userId = request.getHeader("userId");

        if (userId == null) {
            throw new IllegalArgumentException("userId header is missing");
        }

        return new UserDetails(userId);
    }
}
