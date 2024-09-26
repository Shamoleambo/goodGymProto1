package com.tidz.proto.goodgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidz.proto.goodgym.model.Workout;

public interface ExerciseRoutineRepository extends JpaRepository<Workout, Long> {

}
