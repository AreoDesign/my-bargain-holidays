package com.areo.design.holidays.service.response.strategy.impl;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.response.strategy.ResponseParser;
import com.areo.design.holidays.service.response.strategy.ResponseParserProvider;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Component
public class ResponseParserProviderDefault implements ResponseParserProvider {

    private Map<TravelAgency, ResponseParser> parsersByTravelAgency;

    @Override
    public ResponseParser provide(@NotBlank TravelAgency agency) {
        if (parsersByTravelAgency.containsKey(agency)) {
            return parsersByTravelAgency.get(agency);
        }
        throw new NotImplementedException(String.format("No parser found for given agency: %s", agency));
    }

    @Autowired
    public void setParsersByTravelAgency(@Qualifier("responseStrategies") Map<TravelAgency, ResponseParser> parsersByTravelAgency) {
        this.parsersByTravelAgency = parsersByTravelAgency;
    }
}
