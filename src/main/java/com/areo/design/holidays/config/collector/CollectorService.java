package com.areo.design.holidays.config.collector;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.collector.OfferCollectorService;

public interface CollectorService {
    OfferCollectorService getOfferCollectorService();

    TravelAgency getDedicatedTravelAgency();
}
