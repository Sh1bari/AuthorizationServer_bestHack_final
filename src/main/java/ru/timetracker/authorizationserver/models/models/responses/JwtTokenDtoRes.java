package ru.timetracker.authorizationserver.models.models.responses;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDtoRes {
    private String access;
    private String refresh;
}
