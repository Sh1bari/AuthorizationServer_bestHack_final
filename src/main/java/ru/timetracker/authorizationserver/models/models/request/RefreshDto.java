package ru.timetracker.authorizationserver.models.models.request;

import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshDto {
    private String refresh;
}
