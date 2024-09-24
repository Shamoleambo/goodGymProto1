package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
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

		WorkoutDay workoutDay = new WorkoutDay(day.getDate(), day.getWorkout());
		return workoutDayRepository.save(workoutDay);
	}

	public List<WorkoutDay> getAllWorkoutDays() {
		return workoutDayRepository.findAll();
	}

	public WorkoutDay getWorkoutDayById(Long id) {
		return workoutDayRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Workout day " + id));
	}

}
