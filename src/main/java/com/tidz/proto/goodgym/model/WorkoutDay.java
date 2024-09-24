package com.tidz.proto.goodgym.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class WorkoutDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	private LocalDate date;
	@Column(name = "total_score")
	private Double totalScore;

	@OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ExerciseRoutine> workout;

	public WorkoutDay() {

	}

	public WorkoutDay(LocalDate date, List<ExerciseRoutine> workout) {
		this.date = date;

		this.setExercisesOneByOne(workout);
		this.calculateScore(workout);
		this.workout = workout;
	}

	private void setExercisesOneByOne(List<ExerciseRoutine> exercises) {
		for (ExerciseRoutine ex : exercises) {
			ex.setWorkoutDay(this);
		}
	}

	private void calculateScore(List<ExerciseRoutine> exercises) {
		if (!exercises.isEmpty()) {
			Double scoreSum = 0.0;
			setTotalScore(scoreSum);
			for (ExerciseRoutine ex : exercises) {
				scoreSum += ex.getScore();
			}

			setTotalScore(scoreSum / exercises.size());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public List<ExerciseRoutine> getWorkout() {
		return workout;
	}

	public void setWorkout(List<ExerciseRoutine> workout) {
		this.workout = workout;
	}

}
