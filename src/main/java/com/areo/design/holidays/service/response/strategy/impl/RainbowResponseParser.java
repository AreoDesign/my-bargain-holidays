package com.areo.design.holidays.service.response.strategy.impl;

import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.service.response.strategy.ResponseParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser {

    private static final Type rainbowType = new TypeToken<Collection<HotelDto>>() {
    }.getType();

    private final Gson gson;

    @Override
    public Collection<HotelDto> parse(HttpEntity<String> response) {
        String responseBody = response.getBody();
        Map responseBodyMap = gson.fromJson(responseBody, Map.class);
        String jsonWithOffers = gson.toJson(responseBodyMap.get("Bloczki"));
        return gson.fromJson(jsonWithOffers, rainbowType);
    }
}
