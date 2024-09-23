package com.tidz.proto.goodgym.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.ExerciseRoutine;
import com.tidz.proto.goodgym.response.ApiResponse;
import com.tidz.proto.goodgym.service.ExerciseRoutineService;

@RestController
@RequestMapping("/api/routines")
public class ExerciseRoutineController {

	private final ExerciseRoutineService exerciseRoutineService;

	public ExerciseRoutineController(ExerciseRoutineService exerciseService) {
		this.exerciseRoutineService = exerciseService;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse> save(@RequestBody ExerciseRoutine newExercise) {
		try {
			ExerciseRoutine exercise = exerciseRoutineService.save(newExercise);
			return ResponseEntity.ok(new ApiResponse("Exercise Created", exercise));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllExercises() {
		List<ExerciseRoutine> exercises = exerciseRoutineService.getAllExercises();
		return ResponseEntity.ok(new ApiResponse("All Exercises", exercises));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getExerciseById(@PathVariable("id") Long id) {
		try {
			ExerciseRoutine exercise = exerciseRoutineService.getExerciseById(id);
			return ResponseEntity.ok(new ApiResponse("Exercise " + id, exercise));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateExercise(@PathVariable("id") Long id,
			@RequestBody ExerciseRoutine updtExercise) {
		try {
			ExerciseRoutine exercise = exerciseRoutineService.updateExercise(id, updtExercise);
			return ResponseEntity.ok(new ApiResponse("Exercise updated", exercise));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteExerciseById(@PathVariable("id") Long id) {
		try {
			exerciseRoutineService.deleteExercise(id);
			return ResponseEntity.ok(new ApiResponse("Exercise " + id + " deleted", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
