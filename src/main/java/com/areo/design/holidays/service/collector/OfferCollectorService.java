package com.areo.design.holidays.service.collector;

import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;

import java.util.Collection;

public interface OfferCollectorService {
    Collection<HotelDto> collect(SearchCriterionDto criterion);
}
