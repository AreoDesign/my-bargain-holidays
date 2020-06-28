package com.areo.design.holidays.service.response.strategy.impl;

import com.areo.design.holidays.acl.HotelRainbowACL;
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

    private static final String OFFERS_KEY = "Bloczki";
    private static final Type rainbowACLReturnType = new TypeToken<Collection<HotelRainbowACL>>() {
    }.getType();

    private final Gson gson;

    @Override
    public Collection<HotelDto> parse(HttpEntity<String> response) {
        String jsonWithOffers = getContent(response);
        Collection<HotelRainbowACL> offersACL = getACLTranslation(jsonWithOffers);
        return null;
    }

    private Collection<HotelRainbowACL> getACLTranslation(String jsonACL) {
        return gson.fromJson(jsonACL, rainbowACLReturnType);
    }

    private String getContent(HttpEntity<String> response) {
        String responseBody = response.getBody();
        Map responseBodyMap = gson.fromJson(responseBody, Map.class);
        return gson.toJson(responseBodyMap.get(OFFERS_KEY));
    }
}
