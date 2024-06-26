package org.example.apigateway.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.example.apigateway.jwt.JwtTokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

    public GlobalAuthFilter() {
        super(Config.class);
    }

    /*
    Request URI가 인증을 거치지 않을 ExcludeURL에 포함되는지 판단
   포함된다면, chain.filter(exchange)로 아래 필터 로직 수행하지 않고 다음으로 진행
   포함되지 않는다면 아래의 인증 로직 수행
    1. filter 에서 리턴을 해도 컨트롤러처럼 동작하지 않음
    -> throw를 해도 할 수 있는게 없음 -> return on error
    * */
    @Override
    public GatewayFilter apply(Config config) {

        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
//            request.getURI().toString().contains("로그인apiurl") -> 이렇게하면 얘만 뺄수잇음
            HttpHeaders headers = request.getHeaders();

            List<String> strings = headers.get(HttpHeaders.AUTHORIZATION);

            if (strings == null || strings.isEmpty()) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }
            String authorizationHeader = strings.getFirst();
            String jwt = authorizationHeader.replace("Bearer ", "");

            ServerHttpRequest newRequest = null;

            try {
                log.debug("디버그 : 토큰 있음");
                DecodedJWT decodedJWT = JwtTokenProvider.verify(jwt);
                String id = decodedJWT.getClaim("id").asString();
                String roles = decodedJWT.getClaim("role").asString();

                newRequest = request.mutate()
                        .header("userId", id)
                        .header("roles", roles)
                        .build();

                log.debug("디버그 : 인증 객체 만들어짐");
            } catch (SignatureVerificationException sve) {
                log.error("토큰 검증 실패");
            } catch (TokenExpiredException tee) {
                log.error("토큰 만료됨");
            }

            assert newRequest != null;
            return chain.filter(exchange.mutate().request(newRequest).build());

        }));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {
        // 변수 설정은 application.yml과 연동
        // apply 메소드에서 미리 설정한 매개변수를 사용하고 싶다면 application.yml에서 args로 설정 후
        // 해당 static class에 변수로 선언해서 사용할 수 있다.
    }

}
