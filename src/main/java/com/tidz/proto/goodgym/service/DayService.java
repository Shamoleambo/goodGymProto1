package com.tidz.proto.goodgym.service;

import org.springframework.stereotype.Service;

import com.tidz.proto.goodgym.model.Day;
import com.tidz.proto.goodgym.repository.DayRepository;

@Service
public class DayService {

	private final DayRepository dayRepository;

	public DayService(DayRepository dayRepository) {
		this.dayRepository = dayRepository;
	}

	public Day save(Day day) {
		return dayRepository.save(day);
	}

}
