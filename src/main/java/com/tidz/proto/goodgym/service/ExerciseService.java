package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceAlreadyExistsException;
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

}
