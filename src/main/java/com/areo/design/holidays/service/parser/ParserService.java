package com.areo.design.holidays.service.parser;

import com.areo.design.holidays.dto.HotelDto;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ParserService {
    Collection<HotelDto> parse(ResponseEntity response);
}
