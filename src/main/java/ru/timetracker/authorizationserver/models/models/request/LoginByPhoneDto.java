package ru.timetracker.authorizationserver.models.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginByPhoneDto {
    @NotBlank(message = "Username cant be blank")
    private String phone;
}
