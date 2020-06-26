package com.areo.design.holidays.service.response;

import com.areo.design.holidays.dto.HotelDto;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface ParserService {
    Collection<HotelDto> parse(@NotNull ResponseEntity<String> response);
}
