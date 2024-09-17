package com.tidz.proto.goodgym.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
