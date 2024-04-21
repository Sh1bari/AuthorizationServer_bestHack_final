package ru.timetracker.authorizationserver.services;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.timetracker.authorizationserver.exceptions.roles.RoleNotFoundExc;
import ru.timetracker.authorizationserver.models.entities.Role;
import ru.timetracker.authorizationserver.repositories.RoleRepo;

/**
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public Role getUserRole(){
        return roleRepo.findByName("ROLE_USER")
                .orElseThrow(RoleNotFoundExc::new);
    }
}
