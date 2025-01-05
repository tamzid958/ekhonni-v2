package com.ekhonni.backend.config;

import com.ekhonni.backend.exception.InitiatePaymentException;
import com.ekhonni.backend.exception.InvalidTransactionException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Objects;

/**
 * Author: Asif Iqbal
 * Date: 12/28/24
 */
@Configuration
@Slf4j
public class ResilienceConfig {

    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(20))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5)
                .minimumNumberOfCalls(3)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(
                        InitiatePaymentException.class,
                        RestClientException.class)
                .ignoreExceptions(
                        UnsupportedEncodingException.class,
                        InvalidTransactionException.class)
                .build();
    }

    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3)
                .intervalFunction(IntervalFunction.ofExponentialBackoff(
                        Duration.ofSeconds(1),
                        2.0,
                        Duration.ofSeconds(10)
                ))
                .retryExceptions(
                        InitiatePaymentException.class,
                        RestClientException.class,
                        UnsupportedEncodingException.class)
                .ignoreExceptions(InvalidTransactionException.class)
                .build();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry(CircuitBreakerConfig circuitBreakerConfig) {
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreaker = registry.circuitBreaker("initiatePayment", circuitBreakerConfig);

        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> {
                    log.info("Circuit Breaker '{}' state changed from {} to {}",
                            event.getCircuitBreakerName(),
                            event.getStateTransition().getFromState(),
                            event.getStateTransition().getToState());
                })
                .onError(event -> {
                    log.error("Circuit Breaker '{}' recorded an error: {}",
                            event.getCircuitBreakerName(),
                            event.getThrowable().getMessage());
                })
                .onSuccess(event -> {
                    log.debug("Circuit Breaker '{}' recorded a success",
                            event.getCircuitBreakerName());
                });

        return registry;
    }

    @Bean
    public RetryRegistry retryRegistry(RetryConfig retryConfig) {
        RetryRegistry registry = RetryRegistry.of(retryConfig);

        Retry retry = registry.retry("retryPayment", retryConfig);

        retry.getEventPublisher()
                .onRetry(event -> {
                    log.info("Retry attempt {} of {}",
                            event.getNumberOfRetryAttempts(),
                            retryConfig.getMaxAttempts());
                })
                .onError(event -> {
                    log.error("Retry failed: {}", Objects.requireNonNull(event.getLastThrowable()).getMessage());
                })
                .onSuccess(event -> {
                    log.debug("Retry succeeded after {} attempts",
                            event.getNumberOfRetryAttempts());
                });

        return registry;
    }
}
