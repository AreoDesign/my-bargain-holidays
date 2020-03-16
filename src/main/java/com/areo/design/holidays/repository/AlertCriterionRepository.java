package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.AlertCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertCriterionRepository extends JpaRepository<AlertCriterion, Integer> {
}
