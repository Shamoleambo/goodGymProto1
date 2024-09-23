package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.repository.ExerciseRoutineRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseRoutineService {

	private final ExerciseRoutineRepository exerciseRoutineRepository;

	public ExerciseRoutineService(ExerciseRoutineRepository exerciseRoutineRepository) {
		this.exerciseRoutineRepository = exerciseRoutineRepository;
	}

	@Transactional
	public ExerciseRoutine save(ExerciseRoutine exercise) {
		return exerciseRoutineRepository.save(exercise);
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
