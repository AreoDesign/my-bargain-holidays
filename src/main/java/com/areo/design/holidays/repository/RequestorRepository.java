package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.RequestorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestorRepository extends JpaRepository<RequestorEntity, UUID> {

    @EntityGraph(value = "graph.requestor.search_criteria.alert_criteria", type = EntityGraph.EntityGraphType.FETCH)
    Optional<RequestorEntity> findByLogin(String login);

}
