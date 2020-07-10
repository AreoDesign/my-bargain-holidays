package com.areo.design.holidays.component.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.exception.ResponseParseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;

@Slf4j
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser<Map<String, Object>> {

    private static final String OFFERS_KEY = "Bloczki";
    private static final Type rainbowACLReturnType = new TypeToken<Collection<RainbowResponseACL>>() {
    }.getType();

    private final Gson gson;
    private final RainbowACLConverter rainbowACLConverter;

    @Override
    public Collection<HotelDto> parse(HttpEntity<Map<String, Object>> response) throws ResponseParseException {
        long startTime = System.currentTimeMillis();
        log.info("response parsing started");
        LocalDateTime timestamp = Instant.ofEpochMilli(response.getHeaders().getDate())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        String rawResponseJsonACL = Optional.ofNullable(response.getBody().get(OFFERS_KEY))
                .map(gson::toJson)
                .orElseThrow(() -> new ResponseParseException(
                        "It was not possible to find offer's content by known key: " + OFFERS_KEY + " to parse within received response"));
        Collection<RainbowResponseACL> offersACL = convert(rawResponseJsonACL, timestamp);
        Collection<HotelDto> result = offersACL.stream()
                .map(rainbowACLConverter::convert)
                .collect(toCollection(LinkedHashSet::new));
        log.info("response parsed successfully in {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    private Collection<RainbowResponseACL> convert(String jsonACL, LocalDateTime timestamp) {
        Collection<RainbowResponseACL> responses = gson.fromJson(jsonACL, rainbowACLReturnType);
        responses.forEach(response -> response.setTimestamp(timestamp));
        return responses;
    }

}
