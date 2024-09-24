package com.tidz.proto.goodgym.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.WorkoutDayRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkoutDayService {

	private final WorkoutDayRepository workoutDayRepository;
	private final ExerciseRepository exerciseRepository;

	public WorkoutDayService(WorkoutDayRepository dayRepository, ExerciseRepository exerciseRepository) {
		this.workoutDayRepository = dayRepository;
		this.exerciseRepository = exerciseRepository;
	}

	@Transactional
	public WorkoutDay save(WorkoutDay day) {
		for (ExerciseRoutine routine : day.getWorkout()) {
			Exercise exercise = Optional.ofNullable(exerciseRepository.findByName(routine.getExercise().getName()))
					.orElseGet(() -> {
						Exercise newExercise = new Exercise(routine.getExercise().getName(),
								routine.getExercise().getBodyArea());
						return exerciseRepository.save(newExercise);
					});
			routine.setExercise(exercise);
		}
		return workoutDayRepository.save(day);
	}

}
