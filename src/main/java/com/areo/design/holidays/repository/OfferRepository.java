package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.Offer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @EntityGraph(value = "graph.offer.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Offer> findByUrl(String url);

}
