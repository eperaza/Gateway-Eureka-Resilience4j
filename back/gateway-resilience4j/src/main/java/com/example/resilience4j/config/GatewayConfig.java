package com.example.resilience4j.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    
       
        @Bean
        public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder)
        {
            return routeLocatorBuilder.routes()
            .route(r -> r.path("/end1/**")
                    .filters(f -> f.circuitBreaker(c->c.setName("end1Fallback").setFallbackUri("/defaultFallback")))
                    .uri("lb://end1")
                    )
            .route(r -> r.path("/end2/**")
                    .filters(f -> f.circuitBreaker(c->c.setName("end2Fallback").setFallbackUri("/defaultFallback")))
                    .uri("lb://end2")
                    )
                    .build();
        }
    
        @Bean
        public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer()
        {
            return factory->factory.configureDefault(id ->new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofSeconds(2)).build()).build());
        }
    
        @Bean
        public RedisRateLimiter redisRateLimiter()
        {
            return new RedisRateLimiter(1,2);
        }
    
        @Bean
        KeyResolver userKeyResolver()
        {
            return exchange -> Mono.just("1");
        }

    
}
