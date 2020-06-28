package com.areo.design.holidays.service.response.strategy;

import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import org.springframework.http.HttpEntity;

import java.util.Collection;

public interface ResponseParser {
    Collection<HotelDto> parse(HttpEntity<String> response) throws ResponseParseException;
}
