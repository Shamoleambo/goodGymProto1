package com.tidz.proto.goodgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidz.proto.goodgym.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

	Exercise findByName(String name);

}
