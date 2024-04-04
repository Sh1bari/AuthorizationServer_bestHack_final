package ru.timetracker.authorizationserver.models.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.*;
import ru.timetracker.authorizationserver.models.entities.User;
import ru.timetracker.authorizationserver.models.enums.UserStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDtoRes {
    private UUID id;
    private String mail;
    private String username;
    private UserStatus status;
    public static UserDtoRes mapFromEntity(User user){
        UserDtoRes res = UserDtoRes.builder()
                .id(user.getId())
                .mail(user.getMail())
                .username(user.getUsername())
                .status(user.getStatus())
                .build();
        return res;
    }
}
