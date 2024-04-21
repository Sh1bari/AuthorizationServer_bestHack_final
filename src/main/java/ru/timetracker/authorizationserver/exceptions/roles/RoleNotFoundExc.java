package ru.timetracker.authorizationserver.exceptions.roles;

import lombok.*;
import ru.timetracker.authorizationserver.exceptions.GlobalAppException;

/**
 * @author Vladimir Krasnov
 */
public class RoleNotFoundExc extends GlobalAppException {
    public RoleNotFoundExc() {
        super(404, "Role not found");
    }
}
