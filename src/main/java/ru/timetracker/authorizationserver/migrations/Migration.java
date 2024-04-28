package ru.timetracker.authorizationserver.migrations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.timetracker.authorizationserver.models.models.request.RegisterByPhoneDto;
import ru.timetracker.authorizationserver.security.AuthService;

@Component
@RequiredArgsConstructor
public class Migration {
    private final AuthService authService;
    @Async
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() throws InterruptedException {
        initAdmin();
    }
    public void initAdmin(){
        RegisterByPhoneDto res = RegisterByPhoneDto.builder()
                .name("Админ")
                .middleName("Админович")
                .surname("Админов")
                .username("admin")
                .phoneNumber("+76666666666")
                .build();
        try {
            authService.validateRegisterNewUserByPhone(res);
            authService.registerNewUserByPhone("1655", res);
        }catch (Exception e){

        }
    }
}
