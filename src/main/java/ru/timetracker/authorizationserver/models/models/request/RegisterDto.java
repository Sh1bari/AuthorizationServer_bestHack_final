package ru.timetracker.authorizationserver.models.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Username cant be blank")
    private String username;
    @NotBlank(message = "Password cant be blank")
    private String password;


}
