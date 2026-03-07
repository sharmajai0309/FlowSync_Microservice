//package com.flowsync.authService.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.JdkClientHttpRequestFactory;
//import org.springframework.web.client.RestClient;
//
//import java.time.Duration;
//
//@Configuration
//public class RestClientConfig {
//
//    @Value("${services.user-service.url}")
//    private String userServiceUrl;
//
//    @Value("${services.user-service.timeout.connect:5000}")
//    private int connectTimeout;
//
//    @Value("${services.user-service.timeout.read:10000}")
//    private int readTimeout;
//
//    @Bean
//    public RestClient userServiceRestClient() {
//        return RestClient.builder()
//                .baseUrl(userServiceUrl)
//                .requestFactory(clientHttpRequestFactory())
//                .defaultHeader("Content-Type", "application/json")
//                .defaultHeader("Accept", "application/json")
//                // Optional: Add default error handler
//                .defaultStatusHandler(
//                        status -> status.is4xxClientError() || status.is5xxServerError(),
//                        (request, response) -> {
//                            // Will be handled in service layer
//                        }
//                )
//                .build();
//    }
//
//    private ClientHttpRequestFactory clientHttpRequestFactory() {
//        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();
//        factory.setReadTimeout(Duration.ofMillis(readTimeout));
//        return factory;
//    }
//}