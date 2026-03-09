package com.flowSync.apiGateway.Util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoint = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/health"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoint
                    .stream()
                    .noneMatch(uri ->
                            request.getURI()
                                    .getPath()
                                    .contains(uri));
}
