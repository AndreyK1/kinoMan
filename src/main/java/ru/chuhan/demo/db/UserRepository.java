package ru.chuhan.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuhan.demo.entitysecur.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}