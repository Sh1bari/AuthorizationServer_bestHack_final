package ru.timetracker.authorizationserver.exceptions;

import lombok.*;

public class WrongTokenExc extends GlobalAppException{
    public WrongTokenExc() {
        super(409, "Wrong token");
    }
}
