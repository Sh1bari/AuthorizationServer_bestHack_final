package ru.timetracker.authorizationserver.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Vladimir Krasnov
 */
@RequiredArgsConstructor
@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        if (!statusCode.is2xxSuccessful()) {
            return FeignException.errorStatus(methodKey, response);
        }
        return null;
    }

}
