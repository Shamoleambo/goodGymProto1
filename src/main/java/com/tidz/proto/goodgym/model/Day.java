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
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "date")
	private LocalDate date;
	@Column(name = "total_score")
	private Double totalScore;

	@OneToMany(mappedBy = "day", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Workout> workout;

	public Day() {

	}

	public Day(LocalDate date, List<Workout> workout) {
		this.date = date;

		this.setExercisesOneByOne(workout);
		this.calculateScore(workout);
		this.workout = workout;
	}

	private void setExercisesOneByOne(List<Workout> exercises) {
		for (Workout ex : exercises) {
			ex.setDay(this);
		}
	}

	public void calculateScore(List<Workout> exercises) {
		if (!exercises.isEmpty()) {
			Double scoreSum = 0.0;
			setTotalScore(scoreSum);
			for (Workout ex : exercises) {
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

	public List<Workout> getWorkout() {
		return workout;
	}

	public void setWorkout(List<Workout> workout) {
		this.workout = workout;
	}

}
