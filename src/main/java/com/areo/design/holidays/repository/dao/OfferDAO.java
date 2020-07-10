package com.areo.design.holidays.repository.dao;

import com.areo.design.holidays.entity.OfferEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OfferDAO extends JpaRepository<OfferEntity, UUID> {

    @EntityGraph(value = "graph.offer.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<OfferEntity> findByUrl(String url);

}
