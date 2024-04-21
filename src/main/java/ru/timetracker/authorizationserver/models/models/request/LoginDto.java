package ru.timetracker.authorizationserver.models.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "Username cant be blank")
    private String username;
    @NotBlank(message = "Password cant be blank")
    private String password;
}
