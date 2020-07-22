package com.areo.design.holidays.component.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser<RainbowResponseACL> {

    private final RainbowACLConverter rainbowACLConverter;

    @Override
    public Collection<HotelDto> parse(ResponseEntity<RainbowResponseACL> response) throws ResponseParseException {
        long startTime = System.currentTimeMillis();
        log.info("response parsing started");
        Collection<HotelDto> result = rainbowACLConverter.convert(response);
        log.info("response parsed successfully in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

}
