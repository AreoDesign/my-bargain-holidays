package com.areo.design.holidays.service.collector;

import com.areo.design.holidays.valueobjects.offer.Answer;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;

public interface OfferCollectorService {
    Answer collect(SearchCriterion criterion);
}
