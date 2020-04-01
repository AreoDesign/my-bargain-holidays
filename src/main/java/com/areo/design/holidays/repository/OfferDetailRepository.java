package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.OfferDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OfferDetailRepository extends JpaRepository<OfferDetailEntity, Long> {

    Set<OfferDetailEntity> findByOfferUrl(String offerUrl);
}
