package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.ExerciseRoutineRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseRoutineService {

	private final ExerciseRoutineRepository exerciseRoutineRepository;
	private final ExerciseRepository exerciseRepository;

	public ExerciseRoutineService(ExerciseRoutineRepository exerciseRoutineRepository,
			ExerciseRepository exerciseRepository) {
		this.exerciseRoutineRepository = exerciseRoutineRepository;
		this.exerciseRepository = exerciseRepository;
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
	public ExerciseRoutine updateExercise(Long id, ExerciseRoutine updtExercise) {
		ExerciseRoutine exercise = exerciseRoutineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
		exercise.setWorkoutDay(updtExercise.getWorkoutDay());
		exercise.setExerciseLoad(updtExercise.getExerciseLoad());
		exercise.setExercise(updtExercise.getExercise());
		exercise.setScore(updtExercise.getScore());

		return exerciseRoutineRepository.save(exercise);
	}

	@Transactional
	public void deleteExercise(Long id) {
		exerciseRoutineRepository.findById(id).ifPresentOrElse(exerciseRoutineRepository::delete, () -> {
			throw new ResourceNotFoundException("Could not find the Exercise " + id);
		});
	}

}
