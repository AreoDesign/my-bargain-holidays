package com.areo.design.holidays.service.request.strategy.impl;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import com.areo.design.holidays.service.request.strategy.RequestCreatorProvider;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Component
public class RequestCreatorProviderDefault implements RequestCreatorProvider {

    private Map<TravelAgency, RequestCreator> creatorsByTravelAgency;

    @Override
    public RequestCreator provide(@NotBlank TravelAgency agency) {
        if (creatorsByTravelAgency.containsKey(agency)) {
            return creatorsByTravelAgency.get(agency);
        }
        throw new NotImplementedException(String.format("No creator found for given agency: %s", agency));
    }

    @Autowired
    public void setCreatorsByTravelAgency(@Qualifier("requestStrategies") Map<TravelAgency, RequestCreator> creatorsByTravelAgency) {
        this.creatorsByTravelAgency = creatorsByTravelAgency;
    }
}


