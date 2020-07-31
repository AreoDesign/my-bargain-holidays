package com.areo.design.holidays.component.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser {

    private final RainbowACLConverter rainbowACLConverter;

    @Override
    public Collection<HotelDto> parse(Response response) throws ResponseParseException {
        long startTime = System.currentTimeMillis();
        log.info("response parsing started");
        RainbowResponseACL body = Optional.ofNullable((RainbowResponseACL) response.getBody())
                .orElseThrow(() -> new ResponseParseException("Response has no body to parse."));
        if (isEmpty(body.getBloczki())) {
            log.info("response contains no elements to parse. {}", body);
        }
        Collection<HotelDto> result = rainbowACLConverter.convert(body);
        decorateWithTimestamp(result, response.getTimestamp());
        log.info("response parsed successfully in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    private void decorateWithTimestamp(Collection<HotelDto> result, LocalDateTime timestamp) {
        result.forEach(hotelDto -> hotelDto.getOffers()
                .forEach(offerDto -> offerDto.getDetails()
                        .forEach(offerDetailDto -> offerDetailDto.getRequestTime().update(timestamp))));
    }
}
