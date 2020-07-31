package com.areo.design.holidays.component.parser;

import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.exception.ParsingException;

import java.util.Collection;

public interface ResponseParser {
    Collection<HotelDto> parse(Response response) throws ParsingException;
}
