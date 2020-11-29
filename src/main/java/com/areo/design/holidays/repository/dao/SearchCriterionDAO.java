package com.areo.design.holidays.repository.dao;

import com.areo.design.holidays.entity.requestor.SearchCriterionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SearchCriterionDAO extends JpaRepository<SearchCriterionEntity, Integer> {

    List<SearchCriterionEntity> findAllByRequestorId(UUID requesterId);
}
