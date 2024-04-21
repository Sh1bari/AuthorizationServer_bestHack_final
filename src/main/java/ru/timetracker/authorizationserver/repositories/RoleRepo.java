package ru.timetracker.authorizationserver.repositories;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.timetracker.authorizationserver.models.entities.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
