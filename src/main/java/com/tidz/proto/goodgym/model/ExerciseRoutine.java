package com.tidz.proto.goodgym.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExerciseRoutine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "exercise_load", nullable = false)
	private Double exerciseLoad;
	@Column(name = "score", nullable = false)
	private Integer score;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "exercise_id")
	private Exercise exercise;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "workout_day_id")
	private WorkoutDay workoutDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Double getExerciseLoad() {
		return exerciseLoad;
	}

	public void setExerciseLoad(Double exerciseLoad) {
		this.exerciseLoad = exerciseLoad;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public WorkoutDay getWorkoutDay() {
		return workoutDay;
	}

	public void setWorkoutDay(WorkoutDay workoutDay) {
		this.workoutDay = workoutDay;
	}

}
