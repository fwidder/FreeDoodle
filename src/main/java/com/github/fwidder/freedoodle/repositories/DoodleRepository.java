package com.github.fwidder.freedoodle.repositories;

import com.github.fwidder.freedoodle.model.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
}
