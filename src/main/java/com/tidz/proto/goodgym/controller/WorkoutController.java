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
import com.tidz.proto.goodgym.model.Workout;
import com.tidz.proto.goodgym.response.ApiResponse;
import com.tidz.proto.goodgym.service.WorkoutService;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

	private final WorkoutService workoutService;

	public WorkoutController(WorkoutService workoutService) {
		this.workoutService = workoutService;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse> save(@RequestBody Workout newWorkout) {
		try {
			Workout exercise = workoutService.save(newWorkout);
			return ResponseEntity.ok(new ApiResponse("Workout Created", exercise));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllWorkouts() {
		List<Workout> exercises = workoutService.getAllWorkouts();
		return ResponseEntity.ok(new ApiResponse("All Workouts", exercises));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getWorkoutById(@PathVariable("id") Long id) {
		try {
			Workout workout = workoutService.getWorkoutById(id);
			return ResponseEntity.ok(new ApiResponse("Workout " + id, workout));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateWorkout(@PathVariable("id") Long id, @RequestBody Workout updtWorkout) {
		try {
			Workout workout = workoutService.updateWorkout(id, updtWorkout);
			return ResponseEntity.ok(new ApiResponse("Workout updated", workout));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteWorkoutById(@PathVariable("id") Long id) {
		try {
			workoutService.deleteWorkout(id);
			return ResponseEntity.ok(new ApiResponse("Workout " + id + " deleted", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
