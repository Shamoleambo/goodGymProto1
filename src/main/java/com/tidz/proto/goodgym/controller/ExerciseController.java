package com.tidz.proto.goodgym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.response.ApiResponse;
import com.tidz.proto.goodgym.service.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

	private final ExerciseService exerciseService;

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse> save(@RequestBody Exercise exercise) {
		Exercise newExercise = exerciseService.save(exercise);
		return ResponseEntity.ok(new ApiResponse("Success", newExercise));
	}

}
