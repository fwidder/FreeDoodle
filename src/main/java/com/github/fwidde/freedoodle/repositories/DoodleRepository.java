package com.github.fwidde.freedoodle.repositories;

import com.github.fwidde.freedoodle.model.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
}
