package ru.timetracker.authorizationserver.models.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterByPhoneDto {
    @NotBlank(message = "Username cant be blank")
    private String username;
    private String name;
    private String middleName;
    private String surname;
    @Pattern(regexp = "^\\+?[0-9]{1,15}$", message = "Incorrect phone number")
    private String phoneNumber;
}
