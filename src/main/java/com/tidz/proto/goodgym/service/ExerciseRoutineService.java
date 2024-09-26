package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.ExerciseRoutineRepository;
import com.tidz.proto.goodgym.repository.WorkoutDayRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseRoutineService {

	private final ExerciseRoutineRepository exerciseRoutineRepository;
	private final ExerciseRepository exerciseRepository;
	private final WorkoutDayRepository workoutRepository;

	public ExerciseRoutineService(ExerciseRoutineRepository exerciseRoutineRepository,
			ExerciseRepository exerciseRepository, WorkoutDayRepository workoutRepository) {
		this.exerciseRoutineRepository = exerciseRoutineRepository;
		this.exerciseRepository = exerciseRepository;
		this.workoutRepository = workoutRepository;
	}

	@Transactional
	public ExerciseRoutine save(ExerciseRoutine exerciseRoutine) {
		Exercise exercise = Optional.ofNullable(exerciseRepository.findByName(exerciseRoutine.getExercise().getName()))
				.orElseGet(() -> {
					Exercise newExercise = new Exercise(exerciseRoutine.getExercise().getName(),
							exerciseRoutine.getExercise().getBodyArea());
					return exerciseRepository.save(newExercise);
				});

		exerciseRoutine.setExercise(exercise);
		return exerciseRoutineRepository.save(exerciseRoutine);
	}

	public List<ExerciseRoutine> getAllExercises() {
		return exerciseRoutineRepository.findAll();
	}

	public ExerciseRoutine getExerciseById(Long id) {
		return exerciseRoutineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
	}

	@Transactional
	public ExerciseRoutine updateExercise(Long id, ExerciseRoutine updtRoutine) {
		Exercise exercise = this.findExerciseByNameOrCreateANewOne(updtRoutine);

		ExerciseRoutine routine = exerciseRoutineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));

		WorkoutDay day = this.findWorkoutDayThatContainsExerciseRoutine(routine);

		routine.setWorkoutDay(updtRoutine.getWorkoutDay());
		routine.setExerciseLoad(updtRoutine.getExerciseLoad());
		routine.setExercise(exercise);
		routine.setScore(updtRoutine.getScore());
		routine.setWorkoutDay(day);

		day.calculateScore(day.getWorkout());

		return exerciseRoutineRepository.save(routine);
	}

	@Transactional
	public void deleteExercise(Long id) {
		exerciseRoutineRepository.findById(id).ifPresentOrElse(exerciseRoutineRepository::delete, () -> {
			throw new ResourceNotFoundException("Could not find the Exercise " + id);
		});
	}

	private Exercise findExerciseByNameOrCreateANewOne(ExerciseRoutine routine) {
		return Optional.ofNullable(exerciseRepository.findByName(routine.getExercise().getName())).orElseGet(() -> {
			Exercise newExercise = new Exercise(routine.getExercise().getName(), routine.getExercise().getBodyArea());
			return exerciseRepository.save(newExercise);
		});
	}

	public WorkoutDay findWorkoutDayThatContainsExerciseRoutine(ExerciseRoutine routine) {
		return workoutRepository.findAll().stream().filter(workoutDay -> workoutDay.getWorkout().contains(routine))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("workout day not found"));
	}

}
