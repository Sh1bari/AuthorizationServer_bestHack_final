package ru.timetracker.authorizationserver.repositories;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.timetracker.authorizationserver.models.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
