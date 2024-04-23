package ru.timetracker.authorizationserver.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.timetracker.authorizationserver.models.models.request.CreateUserDto;

/**
 * @author Vladimir Krasnov
 */
@Component
public class RemoteServiceFallback implements MainClient {

    @Override
    public ResponseEntity<?> registerNewUser(CreateUserDto req) {
        return bad();
    }

    private ResponseEntity<?> bad(){
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .build();
    }
}
