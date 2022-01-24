package com.github.fwidde.freedoodle.repositories;

import com.github.fwidde.freedoodle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);
}
