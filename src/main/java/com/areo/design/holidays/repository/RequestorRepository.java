package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.Requestor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestorRepository extends JpaRepository<Requestor, UUID> {

    @EntityGraph(value = "graph.requestor.search_criterion.alert_criterion", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Requestor> findByLogin(String login);

}
