package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.Workout;
import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.WorkoutRepository;
import com.tidz.proto.goodgym.repository.WorkoutDayRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkoutService {

	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;
	private final WorkoutDayRepository workoutDayRepository;

	public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository,
			WorkoutDayRepository workoutDayRepository) {
		this.workoutRepository = workoutRepository;
		this.exerciseRepository = exerciseRepository;
		this.workoutDayRepository = workoutDayRepository;
	}

	@Transactional
	public Workout save(Workout exerciseRoutine) {
		Exercise exercise = Optional.ofNullable(exerciseRepository.findByName(exerciseRoutine.getExercise().getName()))
				.orElseGet(() -> {
					Exercise newExercise = new Exercise(exerciseRoutine.getExercise().getName(),
							exerciseRoutine.getExercise().getBodyArea());
					return exerciseRepository.save(newExercise);
				});

		exerciseRoutine.setExercise(exercise);
		return workoutRepository.save(exerciseRoutine);
	}

	public List<Workout> getAllExercises() {
		return workoutRepository.findAll();
	}

	public Workout getExerciseById(Long id) {
		return workoutRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
	}

	@Transactional
	public Workout updateExercise(Long id, Workout updtRoutine) {
		Exercise exercise = this.findExerciseByNameOrCreateANewOne(updtRoutine);

		Workout routine = workoutRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));

		WorkoutDay day = this.findWorkoutDayThatContainsExerciseRoutine(routine);

		routine.setWorkoutDay(updtRoutine.getWorkoutDay());
		routine.setLoad(updtRoutine.getLoad());
		routine.setExercise(exercise);
		routine.setScore(updtRoutine.getScore());
		routine.setWorkoutDay(day);

		day.calculateScore(day.getWorkout());

		return workoutRepository.save(routine);
	}

	@Transactional
	public void deleteExercise(Long id) {
		workoutRepository.findById(id).ifPresentOrElse(workoutRepository::delete, () -> {
			throw new ResourceNotFoundException("Could not find the Exercise " + id);
		});
	}

	private Exercise findExerciseByNameOrCreateANewOne(Workout routine) {
		return Optional.ofNullable(exerciseRepository.findByName(routine.getExercise().getName())).orElseGet(() -> {
			Exercise newExercise = new Exercise(routine.getExercise().getName(), routine.getExercise().getBodyArea());
			return exerciseRepository.save(newExercise);
		});
	}

	public WorkoutDay findWorkoutDayThatContainsExerciseRoutine(Workout routine) {
		return workoutDayRepository.findAll().stream().filter(workoutDay -> workoutDay.getWorkout().contains(routine))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("workout day not found"));
	}

}
