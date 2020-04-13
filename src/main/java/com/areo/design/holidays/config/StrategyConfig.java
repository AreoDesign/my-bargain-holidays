package com.areo.design.holidays.config;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import com.areo.design.holidays.service.request.strategy.impl.rainbow.RainbowRequestCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;

@Configuration
public class StrategyConfig {

    @Autowired
    private RainbowRequestCreator rainbowRequestCreator;

    @Bean
    @Qualifier("requestStrategies")
    public Map<TravelAgency, RequestCreator> requestCreatorProvider() {
        return Map.of(
                RAINBOW_TOURS, rainbowRequestCreator
        );
    }

}
