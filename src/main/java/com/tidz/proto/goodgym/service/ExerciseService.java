package com.tidz.proto.goodgym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.repository.ExerciseRepository;

@Service
public class ExerciseService {

	private final ExerciseRepository exerciseRepository;

	public ExerciseService(ExerciseRepository exerciseRepository) {
		this.exerciseRepository = exerciseRepository;
	}

	public Exercise save(Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	public List<Exercise> getAllExercises() {
		return exerciseRepository.findAll();
	}

}
