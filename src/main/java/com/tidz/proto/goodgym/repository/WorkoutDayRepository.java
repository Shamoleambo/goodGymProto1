package com.tidz.proto.goodgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidz.proto.goodgym.model.WorkoutDay;

public interface WorkoutDayRepository extends JpaRepository<WorkoutDay, Long> {

}
