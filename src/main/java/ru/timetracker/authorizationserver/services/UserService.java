package ru.timetracker.authorizationserver.services;

import lombok.*;
import org.springframework.stereotype.Service;
import ru.timetracker.authorizationserver.exceptions.users.UserNotFoundExc;
import ru.timetracker.authorizationserver.models.entities.User;
import ru.timetracker.authorizationserver.repositories.UserRepo;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public boolean existsByUsername(String username){
        return userRepo.existsByUsername(username);
    }
    public boolean existsByPhoneNumber(String phone){return userRepo.existsByPhoneNumber(phone);}
    public User findByUsername(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(UserNotFoundExc::new);
    }
    public User findByPhoneNumber(String phone){
        return userRepo.findByPhoneNumber(phone)
                .orElseThrow(UserNotFoundExc::new);
    }

    public User findById(UUID id){
        return userRepo.findById(id)
                .orElseThrow(UserNotFoundExc::new);
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }
}
