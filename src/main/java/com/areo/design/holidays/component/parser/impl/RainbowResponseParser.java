package com.areo.design.holidays.component.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.valueobjects.offer.Answer;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RainbowResponseParser implements ResponseParser<RainbowResponse> {

    private final RainbowACLConverter rainbowACLConverter;

    @Override
    public Answer parse(RainbowResponse response) {
        long startTime = System.currentTimeMillis();
        Collection<Hotel> conversionResult = rainbowACLConverter.convert(response);
        Answer answer = Answer.of(conversionResult);
        log.info("response parsed successfully in {} ms", System.currentTimeMillis() - startTime);
        return answer;
    }
}
