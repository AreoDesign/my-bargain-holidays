package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.SearchCriterion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchCriterionRepository extends JpaRepository<SearchCriterion, Integer> {
}
