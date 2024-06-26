package ru.timetracker.authorizationserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.timetracker.authorizationserver.exceptions.GeneralException;
import ru.timetracker.authorizationserver.models.models.request.*;
import ru.timetracker.authorizationserver.models.models.responses.JwtTokenDtoRes;
import ru.timetracker.authorizationserver.models.models.responses.RegisterUserDtoRes;
import ru.timetracker.authorizationserver.security.AuthService;
import ru.timetracker.authorizationserver.utils.JwtUtil;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("")
@Tag(name = "Authorization API", description = "")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterUserDtoRes.class))
                    })
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterUserDtoRes> registerUser(@RequestBody @Valid RegisterDto req){
        RegisterUserDtoRes res = authService.registerNewUser(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokenDtoRes.class))
                    })
    })
    @PostMapping("/login")
    public ResponseEntity<RegisterUserDtoRes> login(@RequestBody @Valid LoginDto req){
        RegisterUserDtoRes res = authService.login(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @Operation(summary = "Refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtTokenDtoRes.class))
                    })
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDtoRes> refresh(@RequestBody @Valid RefreshDto req){
        JwtTokenDtoRes res = authService.refresh(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @PostMapping("/register-by-phone")
    public ResponseEntity<RegisterUserDtoRes> registerByPhone(
            @RequestParam String code,
            @RequestBody @Valid RegisterByPhoneDto req){
        RegisterUserDtoRes res = authService.registerNewUserByPhone(code, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/register-by-phone/validate")
    public ResponseEntity<?> validateRegisterByPhone(@RequestBody @Valid RegisterByPhoneDto req){
        boolean res = authService.validateRegisterNewUserByPhone(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/login-by-phone")
    public ResponseEntity<RegisterUserDtoRes> loginByPhone(
            @RequestParam String code,
            @RequestBody @Valid LoginByPhoneDto req){
        RegisterUserDtoRes res = authService.loginByPhone(code, req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
    @PostMapping("/login-by-phone/validate")
    public ResponseEntity<RegisterUserDtoRes> loginByPhoneValidate(
            @RequestBody @Valid LoginByPhoneDto req){
        boolean res = authService.validateLoginByPhone(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
