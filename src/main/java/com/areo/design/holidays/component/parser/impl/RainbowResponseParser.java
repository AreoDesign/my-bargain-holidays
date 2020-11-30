package com.areo.design.holidays.component.parser.impl;

import com.areo.design.holidays.acl.ResponseBodyACL;
import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseBodyACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.exception.ParsingException;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Slf4j
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser<RainbowResponse> {

    private final RainbowACLConverter rainbowACLConverter;

    private final Function<RainbowResponseBodyACL, RainbowResponseBodyACL> logInfoIfNoOffers = responseBody -> {
        if (isEmpty(responseBody.getBloczki())) log.info("response contains no elements to parse. {}", responseBody);
        return responseBody;
    };

    private final Function<ResponseBodyACL, RainbowResponseBodyACL> convertResponseBodyToRainbowType = responseBody -> (RainbowResponseBodyACL) responseBody;

    @Override
    public Collection<Hotel> parse(RainbowResponse response) throws ParsingException {
        long startTime = System.currentTimeMillis();
        log.info("response parsing started");
        RainbowResponseBodyACL body = Optional.ofNullable(response.getBody())
                .map(convertResponseBodyToRainbowType)
                .map(logInfoIfNoOffers)
                .orElseThrow(() -> new ParsingException("Response has no body to parse."));
        Collection<Hotel> conversionResult = rainbowACLConverter.convert(body);
        updateTimestamp(conversionResult, response.getTimestamp());
        log.info("response parsed successfully in {} ms", System.currentTimeMillis() - startTime);
        return conversionResult;
    }

    private void updateTimestamp(Collection<Hotel> result, LocalDateTime timestamp) {
        result.forEach(hotelDto -> hotelDto.getOffers()
                .forEach(offerDto -> offerDto.getDetails()
                        .forEach(offerDetailDto -> offerDetailDto.getRequestTime().update(timestamp))));
    }
}
