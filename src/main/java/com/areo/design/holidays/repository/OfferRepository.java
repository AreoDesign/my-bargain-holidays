package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.OfferEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, UUID> {

    @EntityGraph(value = "graph.offer.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<OfferEntity> findByUrl(String url);

}
