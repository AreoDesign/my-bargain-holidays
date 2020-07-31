package com.areo.design.holidays.repository.dao;

import com.areo.design.holidays.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OfferDetailDAO extends JpaRepository<DetailEntity, Long> {

    Set<DetailEntity> findByOfferUrl(String offerUrl);
}
