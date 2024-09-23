package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceAlreadyExistsException;
import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.repository.ExerciseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExerciseService {

	private final ExerciseRepository exerciseRepository;

	public ExerciseService(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
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
		exerciseRepository.deleteById(id);
	}

}
