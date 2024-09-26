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
import com.tidz.proto.goodgym.model.Day;
import com.tidz.proto.goodgym.response.ApiResponse;
import com.tidz.proto.goodgym.service.DayService;

@RestController
@RequestMapping("/api/workout-days")
public class DayController {

	private final DayService dayService;

	public DayController(DayService dayService) {
		this.dayService = dayService;
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse> saveWorkoutDay(@RequestBody Day day) {
		try {
			Day newWorkoutDay = dayService.save(day);
			return ResponseEntity.ok(new ApiResponse("WorkoutDay created", newWorkoutDay));
		} catch (Exception e) {
			return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse> getAllWorkoutDays() {
		try {
			List<Day> workoutDays = dayService.getAllDays();
			return ResponseEntity.ok(new ApiResponse("Success", workoutDays));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getWorkoutDayById(@PathVariable("id") Long id) {
		try {
			Day day = dayService.getDayById(id);
			return ResponseEntity.ok(new ApiResponse("Success", day));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
