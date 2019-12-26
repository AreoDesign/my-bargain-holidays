package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.OfferDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OfferDetailRepository extends JpaRepository<OfferDetail, Long> {

    Optional<Set<OfferDetail>> findByOfferUrl(String offerUrl);
}
