package com.comvarse.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitingConfiguration {

    @Bean
    public RedisRateLimiter redisRateLimiter() {

        return new RedisRateLimiter(1, 23, 1);
    }

    @Bean
    public KeyResolver apiKeyResolver() {
        // You can implement any KeyResolver you want based on your requirements
        // For example, you can resolve key based on API key, user ID, IP address, etc.
        //return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}