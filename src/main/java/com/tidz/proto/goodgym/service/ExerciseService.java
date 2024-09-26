package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceAlreadyExistsException;
import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.Workout;
import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.repository.ExerciseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseService {

	private final ExerciseRepository exerciseRepository;
	private final ExerciseRoutineService routineService;

	public ExerciseService(ExerciseRepository exerciseRepository, ExerciseRoutineService routineService) {
		this.exerciseRepository = exerciseRepository;
		this.routineService = routineService;
	}

	@Transactional
	public Exercise save(Exercise exercise) {
		Exercise newExercise = exerciseRepository.findByName(exercise.getName());
		if (newExercise == null) {
			return exerciseRepository.save(exercise);
		} else {
			throw new ResourceAlreadyExistsException("Resource " + exercise.getName() + " already exist");
		}
	}

	public List<Exercise> getAllExercises() {
		return exerciseRepository.findAll();
	}

	public Exercise getExerciseById(Long id) {
		return exerciseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No exercise with id " + id + " found"));
	}

	@Transactional
	public void updateExercise(Long id, Exercise updtExercise) {
		exerciseRepository.findById(id).ifPresentOrElse(exercise -> {
			exercise.setBodyArea(updtExercise.getBodyArea());
			exercise.setName(updtExercise.getName());
			exerciseRepository.save(exercise);
		}, () -> {
			throw new ResourceNotFoundException("No exercise with id " + id + " found");
		});
	}

	@Transactional
	public void deleteExerciseById(Long id) {
		Exercise exercise = exerciseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find Exercise with id " + id));

		for (Workout routine : exercise.getRoutines()) {
			WorkoutDay day = routineService.findWorkoutDayThatContainsExerciseRoutine(routine);
			day.getWorkout().remove(routine);
			day.calculateScore(day.getWorkout());
		}

		exerciseRepository.delete(exercise);
	}

}
