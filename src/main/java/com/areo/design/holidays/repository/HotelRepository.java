package com.areo.design.holidays.repository;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.HotelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, UUID> {

    @EntityGraph(value = "graph.hotel.offers.details", type = EntityGraph.EntityGraphType.FETCH)
    Optional<HotelEntity> findByNameAndCountry(String name, Country country);

}
