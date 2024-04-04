package ru.timetracker.authorizationserver.exceptions;

import lombok.*;

public class GeneralException extends GlobalAppException{
    public GeneralException(int status, String message) {
        super(status, message);
    }
}
