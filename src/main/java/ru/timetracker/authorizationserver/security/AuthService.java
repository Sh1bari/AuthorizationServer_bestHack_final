package ru.timetracker.authorizationserver.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timetracker.authorizationserver.exceptions.GeneralException;
import ru.timetracker.authorizationserver.exceptions.WrongTokenExc;
import ru.timetracker.authorizationserver.exceptions.auth.BadCredentialsExc;
import ru.timetracker.authorizationserver.feign.MainClient;
import ru.timetracker.authorizationserver.models.entities.User;
import ru.timetracker.authorizationserver.models.models.request.*;
import ru.timetracker.authorizationserver.models.models.responses.JwtTokenDtoRes;
import ru.timetracker.authorizationserver.models.models.responses.RegisterUserDtoRes;
import ru.timetracker.authorizationserver.models.models.responses.UserDtoRes;
import ru.timetracker.authorizationserver.services.RoleService;
import ru.timetracker.authorizationserver.services.UserService;
import ru.timetracker.authorizationserver.utils.JwtUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MainClient mainClient;
    String validCode = "1655";
    @Transactional
    public RegisterUserDtoRes registerNewUser(RegisterDto req){
        if(userService.existsByUsername(req.getUsername())){
            throw new GeneralException(409, String.format("User with username %s exists", req.getUsername()));
        }else {
            User user = userService.saveUser(User.builder()
                    .roles(List.of(roleService.getUserRole()))
                    .username(req.getUsername())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .build());
            mainClient.registerNewUser(CreateUserDto.builder()
                            .username(req.getUsername())
                            .userId(user.getId())
                            .name(req.getName())
                            .middleName(req.getMiddleName())
                            .surname(req.getSurname())
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
    public RegisterUserDtoRes login(LoginDto req){
        if(!userService.existsByUsername(req.getUsername())){
            throw new BadCredentialsExc();
        }
        User user = userService.findByUsername(req.getUsername());
        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new BadCredentialsExc();
        }
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

    public RegisterUserDtoRes loginByPhone(String code, LoginByPhoneDto req){
        if(!userService.existsByPhoneNumber(req.getPhone())){
            throw new GeneralException(409, "Номер телефона не существует");
        }else if(!validCode.equals(code)) {
            throw new GeneralException(409, "Неправильный код");
        }
        User user = userService.findByPhoneNumber(req.getPhone());
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
    public boolean validateLoginByPhone(LoginByPhoneDto req){
        if(!userService.existsByPhoneNumber(req.getPhone())){
            throw new GeneralException(409, "Номер телефона не существует");
        }
        return true;
    }

    public JwtTokenDtoRes refresh(RefreshDto req){
        Jws<Claims> claims = JwtUtil.getClaims(req.getRefresh());
        if(!claims.getBody().get("tokenType").toString().equals("refresh")){
            throw new WrongTokenExc();
        }
        String userId = claims.getBody().get("sub").toString();
        User user = userService.findById(UUID.fromString(userId));
        JwtTokenDtoRes res = JwtTokenDtoRes.builder()
                .access(JwtUtil.generateAccessToken(user))
                .refresh(JwtUtil.generateRefreshToken(user))
                .build();
        return res;
    }

    public RegisterUserDtoRes registerNewUserByPhoneAdmin(String code, RegisterByPhoneDto req) {
        if (userService.existsByUsername(req.getUsername())) {
            throw new GeneralException(409, "Такой пользователь уже существует");
        } else if (userService.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new GeneralException(409, "Номер телефона уже существует");
        } else if (!validCode.equals(code)) {
            throw new GeneralException(409, "Неправильный код");
        } else {
            User user = userService.saveUser(User.builder()
                    .roles(List.of(roleService.getUserRole()))
                    .username(req.getUsername())
                    .phoneNumber(req.getPhoneNumber())
                    .build());
            mainClient.registerNewUserAdmin(CreateUserDto.builder()
                    .username(req.getUsername())
                    .phoneNumber(req.getPhoneNumber())
                    .userId(user.getId())
                    .name(req.getName())
                    .middleName(req.getMiddleName())
                    .surname(req.getSurname())
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

    public RegisterUserDtoRes registerNewUserByPhone(String code, RegisterByPhoneDto req) {
        if (userService.existsByUsername(req.getUsername())) {
            throw new GeneralException(409, "Такой пользователь уже существует");
        } else if (userService.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new GeneralException(409, "Номер телефона уже существует");
        } else if (!validCode.equals(code)) {
            throw new GeneralException(409, "Неправильный код");
        } else {
            User user = userService.saveUser(User.builder()
                    .roles(List.of(roleService.getUserRole()))
                    .username(req.getUsername())
                    .phoneNumber(req.getPhoneNumber())
                    .build());
            mainClient.registerNewUser(CreateUserDto.builder()
                    .username(req.getUsername())
                    .phoneNumber(req.getPhoneNumber())
                    .userId(user.getId())
                    .name(req.getName())
                    .middleName(req.getMiddleName())
                    .surname(req.getSurname())
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
    public boolean validateRegisterNewUserByPhone(RegisterByPhoneDto req){
        if(userService.existsByUsername(req.getUsername())){
            throw new GeneralException(409, "Такой пользователь уже существует");
        }else if(userService.existsByPhoneNumber(req.getPhoneNumber())) {
            throw new GeneralException(409, "Номер телефона уже существует");
        }else{
            return true;
        }
    }
}
