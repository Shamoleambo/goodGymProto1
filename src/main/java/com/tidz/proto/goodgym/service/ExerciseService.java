package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.repository.ExerciseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseService {

	private final ExerciseRepository exerciseRepository;

	public ExerciseService(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

	@Transactional
	public ExerciseRoutine save(ExerciseRoutine exercise) {
		return exerciseRepository.save(exercise);
	}

	public List<ExerciseRoutine> getAllExercises() {
		return exerciseRepository.findAll();
	}

	public ExerciseRoutine getExerciseById(Long id) {
		return exerciseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
	}

	@Transactional
	public ExerciseRoutine updateExercise(Long id, ExerciseRoutine updtExercise) {
		ExerciseRoutine exercise = exerciseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
		exercise.setWorkoutDay(updtExercise.getWorkoutDay());
		exercise.setExerciseLoad(updtExercise.getExerciseLoad());
		exercise.setName(updtExercise.getName());
		exercise.setScore(updtExercise.getScore());

		return exerciseRepository.save(exercise);
	}

	@Transactional
	public void deleteExercise(Long id) {
		exerciseRepository.findById(id).ifPresentOrElse(exerciseRepository::delete, () -> {
			throw new ResourceNotFoundException("Could not find the Exercise " + id);
		});
	}

}
