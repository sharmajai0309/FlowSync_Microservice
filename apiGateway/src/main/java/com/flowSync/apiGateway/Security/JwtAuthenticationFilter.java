package com.flowSync.apiGateway.Security;

import com.flowSync.apiGateway.Util.RouteValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;
    private final RouteValidator routeValidator;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)) {
            String authHeader =
                    request.getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }
            String token = authHeader.substring(7);

            Claims claims = jwtService.extractAllClaims(token);
            String email = claims.get("email", String.class);

            String role = claims.get("role", String.class);


            ServerHttpRequest mutated = request.mutate()
                    .header("X-User-Email", email)
                    .header("X-User-Role", role).build();

            return chain.filter(
                    exchange.mutate().request(mutated).build()
            );


        }
        return chain.filter(exchange);


    }
}
