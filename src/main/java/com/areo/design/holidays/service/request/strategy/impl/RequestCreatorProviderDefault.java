package com.areo.design.holidays.service.request.strategy.impl;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import com.areo.design.holidays.service.request.strategy.RequestCreatorProvider;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class RequestCreatorProviderDefault implements RequestCreatorProvider {

    private final Map<TravelAgency, RequestCreator> creatorsByTravelAgency;

    public RequestCreatorProviderDefault(@Qualifier("requestStrategies") Map<TravelAgency, RequestCreator> creatorsByTravelAgency) {
        this.creatorsByTravelAgency = creatorsByTravelAgency;
    }

    @Override
    public RequestCreator provide(@NotNull TravelAgency agency) {
        if (creatorsByTravelAgency.containsKey(agency)) {
            return creatorsByTravelAgency.get(agency);
        }
        throw new NotImplementedException(String.format("No creator found for given agency: %s", agency));
    }

}


