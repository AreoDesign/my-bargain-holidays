package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.SearchCriterionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchCriterionRepository extends JpaRepository<SearchCriterionEntity, Integer> {

    List<SearchCriterionEntity> findAllByRequestorId(UUID requesterId);
}
