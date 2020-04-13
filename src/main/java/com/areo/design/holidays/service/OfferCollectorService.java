package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.SearchCriterionDto;
import org.springframework.http.HttpStatus;

public interface OfferCollectorService {
    HttpStatus collect(TravelAgency agency, SearchCriterionDto criterion);
}
