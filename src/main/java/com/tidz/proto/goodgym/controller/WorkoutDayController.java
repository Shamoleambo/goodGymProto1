package com.tidz.proto.goodgym.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.WorkoutDay;
import com.tidz.proto.goodgym.response.ApiResponse;
import com.tidz.proto.goodgym.service.WorkoutDayService;

@RestController
@RequestMapping("/api/workout-days")
public class WorkoutDayController {

	private final WorkoutDayService workoutDayService;

	public WorkoutDayController(WorkoutDayService workoutDayService) {
		this.workoutDayService = workoutDayService;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse> saveWorkoutDay(@RequestBody WorkoutDay workoutDay) {
		try {
			WorkoutDay newWorkoutDay = workoutDayService.save(workoutDay);
			return ResponseEntity.ok(new ApiResponse("WorkoutDay created", newWorkoutDay));
		} catch (Exception e) {
			return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllWorkoutDays() {
		try {
			List<WorkoutDay> workoutDays = workoutDayService.getAllWorkoutDays();
			return ResponseEntity.ok(new ApiResponse("Success", workoutDays));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getWorkoutDayById(@PathVariable("id") Long id) {
		try {
			WorkoutDay day = workoutDayService.getWorkoutDayById(id);
			return ResponseEntity.ok(new ApiResponse("Success", day));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
