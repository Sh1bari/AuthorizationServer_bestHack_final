package ru.timetracker.authorizationserver.services;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Vladimir Krasnov
 */
//@FeignClient("main-server")
public interface TestClient {
    @RequestMapping("/api/auth/")
    String greeting();
}
