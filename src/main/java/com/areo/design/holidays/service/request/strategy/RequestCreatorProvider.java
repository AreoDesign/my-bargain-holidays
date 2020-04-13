package com.areo.design.holidays.service.request.strategy;

import com.areo.design.holidays.dictionary.TravelAgency;

import javax.validation.constraints.NotBlank;

public interface RequestCreatorProvider {
    RequestCreator provide(@NotBlank TravelAgency agency);
}
