package com.areo.design.holidays.component.parser;

import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.exception.ParsingException;
import com.areo.design.holidays.valueobjects.offer.Hotel;

import java.util.Collection;

public interface ResponseParser<T extends Response> {
    Collection<Hotel> parse(T response) throws ParsingException;
}
