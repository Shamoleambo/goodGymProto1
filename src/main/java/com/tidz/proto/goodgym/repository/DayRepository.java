package com.tidz.proto.goodgym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidz.proto.goodgym.model.Day;

public interface DayRepository extends JpaRepository<Day, Long> {

}
