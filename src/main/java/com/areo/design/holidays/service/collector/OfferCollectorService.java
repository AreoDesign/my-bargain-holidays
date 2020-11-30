package com.areo.design.holidays.service.collector;

import com.areo.design.holidays.valueobjects.offer.Hotel;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;

import java.util.Collection;

public interface OfferCollectorService {
    Collection<Hotel> collect(SearchCriterion criterion);
}
