package com.areo.design.holidays.component.parser;

import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import org.springframework.http.HttpEntity;

import java.util.Collection;

public interface ResponseParser<T> {
    Collection<HotelDto> parse(HttpEntity<T> response) throws ResponseParseException;
}
