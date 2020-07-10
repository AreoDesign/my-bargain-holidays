package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.Status;
import com.areo.design.holidays.dictionary.TravelAgency;

import javax.validation.constraints.NotNull;

public interface HolidayOfferService {
    Status getOffers();

    Status getOffers(@NotNull TravelAgency travelAgency);
}
