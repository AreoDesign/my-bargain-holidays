package com.areo.design.holidays.config;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import com.areo.design.holidays.service.request.strategy.impl.RainbowRequestCreator;
import com.areo.design.holidays.service.response.strategy.ResponseParser;
import com.areo.design.holidays.service.response.strategy.impl.RainbowResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;

@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    //REQUEST CREATORS
    private final RainbowRequestCreator rainbowRequestCreator;

    //RESPONSE PARSERS
    private final RainbowResponseParser rainbowResponseParser;

    @Bean
    @Qualifier("requestStrategies")
    public Map<TravelAgency, RequestCreator> requestCreatorProvider() {
        return Map.of(
                RAINBOW_TOURS, rainbowRequestCreator
        );
    }

    @Bean
    @Qualifier("responseStrategies")
    public Map<TravelAgency, ResponseParser> responseParserProvider() {
        return Map.of(
                RAINBOW_TOURS, rainbowResponseParser
        );
    }
}
