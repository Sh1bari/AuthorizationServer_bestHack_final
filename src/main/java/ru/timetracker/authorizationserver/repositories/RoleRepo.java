package ru.timetracker.authorizationserver.repositories;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.timetracker.authorizationserver.models.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
