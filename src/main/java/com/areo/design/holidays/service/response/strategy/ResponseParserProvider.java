package com.areo.design.holidays.service.response.strategy;

import com.areo.design.holidays.dictionary.TravelAgency;

import javax.validation.constraints.NotBlank;

public interface ResponseParserProvider {
    ResponseParser provide(@NotBlank TravelAgency agency);
}
