package com.tidz.proto.goodgym.service;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.repository.WorkoutDayRepository;

@Service
public class WorkoutDayService {

	private final WorkoutDayRepository workoutDayRepository;

	public WorkoutDayService(WorkoutDayRepository dayRepository) {
		this.workoutDayRepository = dayRepository;
	}

	public WorkoutDay save(WorkoutDay day) {
		return workoutDayRepository.save(day);
	}

}
