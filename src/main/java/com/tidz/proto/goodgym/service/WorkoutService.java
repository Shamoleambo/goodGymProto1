package com.tidz.proto.goodgym.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.exceptions.ResourceNotFoundException;
import com.tidz.proto.goodgym.model.Day;
import com.tidz.proto.goodgym.model.Exercise;
import com.tidz.proto.goodgym.model.Workout;
import com.tidz.proto.goodgym.repository.DayRepository;
import com.tidz.proto.goodgym.repository.ExerciseRepository;
import com.tidz.proto.goodgym.repository.WorkoutRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkoutService {

	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;
	private final DayRepository dayRepository;

	public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository,
			DayRepository dayRepository) {
		this.workoutRepository = workoutRepository;
		this.exerciseRepository = exerciseRepository;
		this.dayRepository = dayRepository;
	}

	@Transactional
	public Workout save(Workout workout) {
		Exercise exercise = Optional.ofNullable(exerciseRepository.findByName(workout.getExercise().getName()))
				.orElseGet(() -> {
					Exercise newExercise = new Exercise(workout.getExercise().getName(),
							workout.getExercise().getBodyArea());
					return exerciseRepository.save(newExercise);
				});

		workout.setExercise(exercise);
		return workoutRepository.save(workout);
	}

	public List<Workout> getAllWorkouts() {
		return workoutRepository.findAll();
	}

	public Workout getWorkoutById(Long id) {
		return workoutRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));
	}

	@Transactional
	public Workout updateWorkout(Long id, Workout updtWorkout) {
		Exercise exercise = this.findExerciseByNameOrCreateANewOne(updtWorkout);

		Workout workout = workoutRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find the Exercise " + id));

		Day day = this.findDayThatContainsWorkout(workout);

		workout.setDay(updtWorkout.getDay());
		workout.setWeight(updtWorkout.getWeight());
		workout.setExercise(exercise);
		workout.setScore(updtWorkout.getScore());
		workout.setDay(day);

		day.calculateScore(day.getWorkout());

		return workoutRepository.save(workout);
	}

	@Transactional
	public void deleteWorkout(Long id) {
		workoutRepository.findById(id).ifPresentOrElse(workoutRepository::delete, () -> {
			throw new ResourceNotFoundException("Could not find the Exercise " + id);
		});
	}

	private Exercise findExerciseByNameOrCreateANewOne(Workout workout) {
		return Optional.ofNullable(exerciseRepository.findByName(workout.getExercise().getName())).orElseGet(() -> {
			Exercise newExercise = new Exercise(workout.getExercise().getName(), workout.getExercise().getBodyArea());
			return exerciseRepository.save(newExercise);
		});
	}

	public Day findDayThatContainsWorkout(Workout workout) {
		return dayRepository.findAll().stream().filter(workoutDay -> workoutDay.getWorkout().contains(workout))
				.findFirst().orElseThrow(() -> new ResourceNotFoundException("workout day not found"));
	}

}
