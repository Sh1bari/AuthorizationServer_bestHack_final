package ru.timetracker.authorizationserver.models.models.responses;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDtoRes {
    @JsonUnwrapped
    private UserDtoRes user;
    private JwtTokenDtoRes jwtTokens;
}
