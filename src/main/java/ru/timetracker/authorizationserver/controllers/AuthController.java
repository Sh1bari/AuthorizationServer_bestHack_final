package ru.timetracker.authorizationserver.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.timetracker.authorizationserver.models.models.request.LoginDto;
import ru.timetracker.authorizationserver.models.models.request.RegisterDto;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterUserDtoRes> registerUser(@RequestBody @Valid RegisterDto req){
        RegisterUserDtoRes res = authService.registerNewUser(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDtoRes> login(@RequestBody @Valid LoginDto req){
        JwtTokenDtoRes res = authService.login(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/test")
    public String generateToken(){
        return "test";
    }
    @PostMapping("/valid")
    public Boolean validateToken(@RequestParam String token){
        return JwtUtil.validateToken(token);
    }
}
