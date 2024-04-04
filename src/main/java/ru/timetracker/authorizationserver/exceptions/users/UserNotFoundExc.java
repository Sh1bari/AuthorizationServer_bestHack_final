package ru.timetracker.authorizationserver.exceptions.users;

import lombok.*;
import ru.timetracker.authorizationserver.exceptions.GlobalAppException;

public class UserNotFoundExc extends GlobalAppException {
    public UserNotFoundExc() {
        super(404, "User not found");
    }
}
