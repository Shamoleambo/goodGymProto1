package com.tidz.proto.goodgym.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exercises")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "body_area")
	@Enumerated(EnumType.STRING)
	private BodyArea bodyArea;

	@OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Workout> workout;

	public Exercise() {

	}

	public Exercise(String name, BodyArea bodyArea) {
		super();
		this.name = name;
		this.bodyArea = bodyArea;
	}

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

	public BodyArea getBodyArea() {
		return bodyArea;
	}

	public void setBodyArea(BodyArea bodyArea) {
		this.bodyArea = bodyArea;
	}

	public List<Workout> getWorkout() {
		return workout;
	}

	public void setWorkout(List<Workout> workout) {
		this.workout = workout;
	}

}
