package com.areo.design.holidays.service;

import com.areo.design.holidays.config.collector.CollectorService;
import com.areo.design.holidays.dictionary.Status;
import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.exception.NoAnswerFromCollectorServiceException;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.areo.design.holidays.valueobjects.offer.Answer;
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
        Collection<Answer> answers = collectorServices.stream()
                .map(CollectorService::getOfferCollectorService)
                .map(offerCollectorService -> getAnswer(criterion, offerCollectorService))
                .collect(toCollection(HashSet::new));
        log.warn("NO PERSISTENT STORAGE IMPLEMENTATION READY!");
        log.info("Results: {}", answers);

        return Status.SUCCESS;
    }

    @Override
    public Status getOffers(@NotNull TravelAgency travelAgency) {
        log.info("starting data collection for travel agency: {}", travelAgency);
        SearchCriterion criterion = StarterDataProvider.prepareSearchCriterionStub();
        Answer answer = collectorServices.stream()
                .filter(collectorService -> travelAgency.equals(collectorService.getDedicatedTravelAgency()))
                .findFirst()
                .map(CollectorService::getOfferCollectorService)
                .map(service -> getAnswer(criterion, service))
                .orElseThrow(NoAnswerFromCollectorServiceException::new);
        log.warn("NO PERSISTENT STORAGE IMPLEMENTATION READY!");
        log.info("Results:");
        answer.getHotelsWithTravelOffers().forEach(result -> log.info("{}", result));

        return Status.SUCCESS;
    }

    private Answer getAnswer(@NotNull SearchCriterion criterion, OfferCollectorService offerCollectorService) {
        long start = System.currentTimeMillis();
        Answer answer = offerCollectorService.collect(criterion);
        String serviceSimpleName = offerCollectorService.getClass().getSimpleName();
        if (answer.isEmpty()) {
            log.warn("Collector service {} returned no results.", serviceSimpleName);
        } else {
            log.info("Collector service {} returned {} offers for {} hotels.", serviceSimpleName, countCollectedOffers(answer.getHotelsWithTravelOffers()), answer.getHotelsWithTravelOffers().size());
            log.info("Overall offers collection from service {} took: {} ms.", serviceSimpleName, System.currentTimeMillis() - start);
        }
        return answer.sortedByPriceAscending();
    }

    private long countCollectedOffers(Collection<Hotel> result) {
        return result.stream()
                .map(Hotel::getOffers)
                .count();
    }

}
