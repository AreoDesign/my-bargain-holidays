package com.areo.design.holidays.repository.dao;

import com.areo.design.holidays.entity.OfferDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OfferDetailDAO extends JpaRepository<OfferDetailEntity, Long> {

    Set<OfferDetailEntity> findByOfferUrl(String offerUrl);
}
