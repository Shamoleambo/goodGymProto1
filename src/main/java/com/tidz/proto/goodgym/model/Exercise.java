package com.tidz.proto.goodgym.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 255)
	private String name;
	@Column(name = "exercise_load", nullable = false)
	private Double exerciseLoad;
	@Column(name = "score", nullable = false)
	private Integer score;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "workout_day_id")
	private WorkoutDay workoutDay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
