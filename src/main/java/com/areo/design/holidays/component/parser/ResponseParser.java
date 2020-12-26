package com.areo.design.holidays.component.parser;

import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.exception.ParsingException;
import com.areo.design.holidays.valueobjects.offer.Answer;

public interface ResponseParser<T extends Response> {
    Answer parse(T response) throws ParsingException;
}
