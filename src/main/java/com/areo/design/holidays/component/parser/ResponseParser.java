package com.areo.design.holidays.component.parser;

import com.areo.design.holidays.acl.ResponseACL;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ResponseParser<RES extends ResponseACL> {
    Collection<HotelDto> parse(ResponseEntity<RES> response) throws ResponseParseException;
}
