package com.github.fwidder.freedoodle.repositories;

import com.github.fwidder.freedoodle.model.Doodle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoodleRepository extends JpaRepository<Doodle, Long> {
	List<Doodle> findAllByName(String name);
	
	List<Doodle> findAllByCreator(String creator);
}
