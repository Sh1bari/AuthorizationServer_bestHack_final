package ru.timetracker.authorizationserver.security;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.timetracker.authorizationserver.exceptions.GeneralException;
import ru.timetracker.authorizationserver.models.entities.User;
import ru.timetracker.authorizationserver.models.models.request.RegisterDto;
import ru.timetracker.authorizationserver.models.models.responses.JwtTokenDtoRes;
import ru.timetracker.authorizationserver.models.models.responses.RegisterUserDtoRes;
import ru.timetracker.authorizationserver.models.models.responses.UserDtoRes;
import ru.timetracker.authorizationserver.services.RoleService;
import ru.timetracker.authorizationserver.services.UserService;
import ru.timetracker.authorizationserver.utils.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserDtoRes registerNewUser(RegisterDto req){
        if(userService.existsByUsername(req.getUsername())){
            throw new GeneralException(409, String.format("User with username %s exists", req.getUsername()));
        }else {
            User user = userService.saveUser(User.builder()
                    .roles(List.of(roleService.getUserRole()))
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .build());
            JwtTokenDtoRes jwt = JwtTokenDtoRes.builder()
                    .access(JwtUtil.generateAccessToken(user))
                    .refresh(JwtUtil.generateRefreshToken(user))
                    .build();
            RegisterUserDtoRes res = RegisterUserDtoRes.builder()
                    .user(UserDtoRes.mapFromEntity(user))
                    .jwtTokens(jwt)
                    .build();
            return res;
        }
    }
}
