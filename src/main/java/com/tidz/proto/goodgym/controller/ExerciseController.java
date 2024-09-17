package com.tidz.proto.goodgym.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.service.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

	private final ExerciseService exerciseService;

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@PostMapping("")
	public ResponseEntity<Exercise> save(@RequestBody Exercise newExercise) {
		Exercise exercise = exerciseService.save(newExercise);
		return ResponseEntity.ok(exercise);
	}

	@GetMapping("")
	public ResponseEntity<List<Exercise>> getAllExercises() {
		List<Exercise> exercises = exerciseService.getAllExercises();
		return ResponseEntity.ok(exercises);
	}

}
