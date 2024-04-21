package ru.timetracker.authorizationserver.exceptions.auth;

import lombok.*;
import ru.timetracker.authorizationserver.exceptions.GlobalAppException;

/**
 * @author Vladimir Krasnov
 */
public class BadCredentialsExc extends GlobalAppException {
    public BadCredentialsExc() {
        super(409, "Wrong username or password");
    }
}
