package ru.timetracker.authorizationserver.feign;

import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vladimir Krasnov
 */
@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final CustomErrorDecoder customErrorDecoder;

    @Bean
    public ErrorDecoder errorDecoder() {
        return customErrorDecoder;
    }
}
