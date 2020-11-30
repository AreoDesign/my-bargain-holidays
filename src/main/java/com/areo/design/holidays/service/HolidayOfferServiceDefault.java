package com.areo.design.holidays.service;

import com.areo.design.holidays.config.collector.CollectorService;
import com.areo.design.holidays.dictionary.Status;
import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
@RequiredArgsConstructor
public class HolidayOfferServiceDefault implements HolidayOfferService {

    private final List<CollectorService> collectorServices;

    @Override
    public Status getOffers() {
        log.info("starting data collection for all implemented travel agencies: {}", TravelAgency.values());
        SearchCriterion criterion = StarterDataProvider.prepareSearchCriterionStub();
        Collection<Hotel> results = collectorServices.stream()   //FIXME: parallelStream
                .map(CollectorService::getOfferCollectorService)
                .map(offerCollectorService -> getOffersFromService(criterion, offerCollectorService))
                .flatMap(Collection::stream)                        //FIXME: parallelStream
                .collect(toCollection(HashSet::new));
        log.warn("NEED TO IMPLEMENT PERSISTENT STORAGE");
        log.info("Results: {}", results);

        return Status.SUCCESS;
    }

    @Override
    public Status getOffers(@NotNull TravelAgency travelAgency) {
        log.info("starting data collection for travel agency: {}", travelAgency);
        SearchCriterion criterion = StarterDataProvider.prepareSearchCriterionStub();
        Collection<Hotel> results = collectorServices.stream()
                .filter(collectorService -> travelAgency.equals(collectorService.getDedicatedTravelAgency()))
                .map(CollectorService::getOfferCollectorService)
                .map(offerCollectorService -> getOffersFromService(criterion, offerCollectorService))
                .flatMap(Collection::stream)
                .collect(toCollection(HashSet::new));
        log.warn("NEED TO IMPLEMENT PERSISTENT STORAGE");
        log.info("Results:");
        results.forEach(result -> log.info("{}", result));

        return Status.SUCCESS;
    }

    private Collection<Hotel> getOffersFromService(@NotNull SearchCriterion criterion, OfferCollectorService offerCollectorService) {
        long start = System.currentTimeMillis();
        Collection<Hotel> result = offerCollectorService.collect(criterion);
        String serviceSimpleName = offerCollectorService.getClass().getSimpleName();
        if (result.isEmpty()) {
            log.warn("Collector service {} returned no results.", serviceSimpleName);
        } else {
            log.info("Collector service {} returned {} offers for {} hotels.", serviceSimpleName, countCollectedOffers(result), result.size());
            log.info("Collecting offers from service {} took: {} ms.", serviceSimpleName, System.currentTimeMillis() - start);
        }
        return result;
    }

    private long countCollectedOffers(Collection<Hotel> result) {
        return result.stream()
                .map(Hotel::getOffers)
                .count();
    }

}
