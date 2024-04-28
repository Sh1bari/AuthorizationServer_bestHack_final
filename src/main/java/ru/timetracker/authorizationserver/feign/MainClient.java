package ru.timetracker.authorizationserver.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.timetracker.authorizationserver.models.models.request.CreateUserDto;

/**
 * @author Vladimir Krasnov
 */
@Primary
@FeignClient(name = "main-server", configuration = FeignConfig.class)
public interface MainClient {
    @PostMapping("/api/main/auth/user")
    ResponseEntity<?> registerNewUser(CreateUserDto req);
    @PostMapping("/api/main/auth/user/admin")
    ResponseEntity<?> registerNewUserAdmin(CreateUserDto req);
}
