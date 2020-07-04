package com.areo.design.holidays.service.response.strategy.impl;

import com.areo.design.holidays.acl.impl.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.RainbowResponseACL;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.OfferDto;
import com.areo.design.holidays.service.response.strategy.ResponseParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;

@Component
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser {

    private static final String OFFERS_KEY = "Bloczki";
    private static final Type rainbowACLReturnType = new TypeToken<Collection<RainbowResponseACL>>() {
    }.getType();

    private final Gson gson;
    private final RainbowACLConverter rainbowACLConverter;

    @Override
    public Collection<HotelDto> parse(HttpEntity<String> response) {
        LocalDateTime timestamp = Instant.ofEpochMilli(response.getHeaders().getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        String rawResponseJsonACL = getValuableContent(response);
        Collection<RainbowResponseACL> offersACL = translate(rawResponseJsonACL);
        return offersACL.stream()
                .map(rainbowResponseACL -> convertToHotelDto(timestamp, rainbowResponseACL))
                .collect(toCollection(HashSet::new));
    }

    private HotelDto convertToHotelDto(LocalDateTime timestamp, RainbowResponseACL rainbowResponseACL) {
        HotelDto hotelDto = rainbowACLConverter.convert(rainbowResponseACL);
        hotelDto.getOffers().forEach(offer -> setRequestTime(offer, timestamp));
        return hotelDto;
    }

    private Collection<RainbowResponseACL> translate(String jsonACL) {
        return gson.fromJson(jsonACL, rainbowACLReturnType);
    }

    private String getValuableContent(HttpEntity<String> response) {
        String responseBody = response.getBody();
        Map responseBodyMap = gson.fromJson(responseBody, Map.class);
        return gson.toJson(responseBodyMap.get(OFFERS_KEY));
    }

    public void setRequestTime(OfferDto offer, LocalDateTime dateFromResponseHeader) {
        offer.getOfferDetails().forEach(offerDetailDto -> offerDetailDto.setRequestTime(dateFromResponseHeader));
    }
}
