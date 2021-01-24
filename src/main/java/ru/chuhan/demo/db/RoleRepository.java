package ru.chuhan.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuhan.demo.entitysecur.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}