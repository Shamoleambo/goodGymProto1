package com.tidz.proto.goodgym.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tidz.proto.goodgym.exceptions.ResourceAlreadyExistsException;
import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
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
		try {
			Exercise newExercise = exerciseService.save(exercise);
			return ResponseEntity.ok(new ApiResponse("Success", newExercise));
		} catch (ResourceAlreadyExistsException e) {
			return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllExercises() {
		try {
			List<Exercise> exercises = exerciseService.getAllExercises();
			return ResponseEntity.ok(new ApiResponse("Success", exercises));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getExerciseById(@PathVariable("id") Long id) {
		try {
			Exercise exerciseFound = exerciseService.getExerciseById(id);
			return ResponseEntity.ok(new ApiResponse("Success", exerciseFound));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateExercise(@PathVariable("id") Long id, @RequestBody Exercise exercise) {
		try {
			exerciseService.updateExercise(id, exercise);
			Exercise updtExercise = exerciseService.getExerciseById(id);
			return ResponseEntity.ok(new ApiResponse("Success", updtExercise));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteExercise(@PathVariable("id") Long id) {
		try {
			exerciseService.deleteExerciseById(id);
			return ResponseEntity.ok(new ApiResponse("Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
