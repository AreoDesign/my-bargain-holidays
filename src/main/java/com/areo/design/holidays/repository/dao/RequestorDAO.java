package com.areo.design.holidays.repository.dao;

import com.areo.design.holidays.entity.RequestorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RequestorDAO extends JpaRepository<RequestorEntity, UUID> {

    @EntityGraph(value = "graph.requestor.search_criteria.alert_criteria", type = EntityGraph.EntityGraphType.FETCH)
    Optional<RequestorEntity> findByLogin(String login);

}
