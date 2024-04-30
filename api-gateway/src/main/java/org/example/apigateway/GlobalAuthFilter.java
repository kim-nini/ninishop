package org.example.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

    /*
    Request URI가 인증을 거치지 않을 ExcludeURL에 포함되는지 판단
   포함된다면, chain.filter(exchange)로 아래 필터 로직 수행하지 않고 다음으로 진행
   포함되지 않는다면 아래의 인증 로직 수행
    * */
    @Override
    public GatewayFilter apply(Config config) {
        final ServerHttpRequest request; // 요청관련 정보객체
        final ServerHttpResponse response; // 응답 관련 정보객체


        return null;
    }

    public static class Config{
        // 변수 설정은 application.yml과 연동
        // apply 메소드에서 미리 설정한 매개변수를 사용하고 싶다면 application.yml에서 args로 설정 후
        // 해당 static class에 변수로 선언해서 사용할 수 있다.
    }

}
