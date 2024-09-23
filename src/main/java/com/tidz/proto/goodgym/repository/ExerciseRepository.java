package com.tidz.proto.goodgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidz.proto.goodgym.model.ExerciseRoutine;

public interface ExerciseRepository extends JpaRepository<ExerciseRoutine, Long> {

}
