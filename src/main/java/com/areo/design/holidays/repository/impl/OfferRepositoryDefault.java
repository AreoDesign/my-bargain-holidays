package com.areo.design.holidays.repository.impl;

import com.areo.design.holidays.repository.OfferRepository;
import com.areo.design.holidays.repository.dao.HotelDAO;
import com.areo.design.holidays.repository.dao.OfferDAO;
import com.areo.design.holidays.repository.dao.OfferDetailDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryDefault implements OfferRepository {
    private final HotelDAO hotelDAO;
    private final OfferDAO offerDAO;
    private final OfferDetailDAO offerDetailDAO;
}
