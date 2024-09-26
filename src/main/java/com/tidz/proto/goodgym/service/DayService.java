package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.Workout;
import com.tidz.proto.goodgym.model.Day;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.DayRepository;

import jakarta.transaction.Transactional;

@Service
public class DayService {

	private final DayRepository workoutDayRepository;
	private final ExerciseRepository exerciseRepository;

	public DayService(DayRepository dayRepository, ExerciseRepository exerciseRepository) {
		this.workoutDayRepository = dayRepository;
		this.exerciseRepository = exerciseRepository;
	}

	@Transactional
	public Day save(Day day) {
		for (Workout routine : day.getWorkout()) {
			Exercise exercise = Optional.ofNullable(exerciseRepository.findByName(routine.getExercise().getName()))
					.orElseGet(() -> {
						Exercise newExercise = new Exercise(routine.getExercise().getName(),
								routine.getExercise().getBodyArea());
						return exerciseRepository.save(newExercise);
					});
			routine.setExercise(exercise);
		}

		Day workoutDay = new Day(day.getDate(), day.getWorkout());
		return workoutDayRepository.save(workoutDay);
	}

	public List<Day> getAllDays() {
		return workoutDayRepository.findAll();
	}

	public Day getDayById(Long id) {
		return workoutDayRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Workout day " + id));
	}

}
