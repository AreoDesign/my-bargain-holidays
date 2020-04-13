package com.areo.design.holidays.service.request.payload.impl.rainbow;

import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.request.payload.PayloadPreparator;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.areo.design.holidays.service.request.payload.Payload.translate;
import static com.areo.design.holidays.service.request.payload.Payload.translateComplex;
import static java.util.List.copyOf;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class RainbowPayloadPreparator implements PayloadPreparator {

    private final DateTimeFormatter dateTimeFormatter;
    private final DecimalFormat decimalFormatter;

    public RainbowPayloadPreparator(@Qualifier("dateFormatter") DateTimeFormatter dateTimeFormatter,
                                    @Qualifier("doubleToStringOnePlaceAfterCommaFormatter") DecimalFormat decimalFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormatter = decimalFormatter;
    }

    @Override
    public RainbowPayload prepare(SearchCriterionDto criterion) {
        RainbowPayload payload = new RainbowPayload();
        payload.setMiastaWyjazdu(copyOf(translate(payload.getCityAirportCodeTranslation(), criterion.getDepartureCities())));
        payload.setPanstwa(copyOf(translateComplex(payload.getDestinationTranslation(), criterion.getCountries())));
        payload.setRegiony(null);//TODO: in RELEASE 2
        payload.setTerminWyjazduMin(criterion.getDepartureDateFrom().format(dateTimeFormatter));
        payload.setTerminWyjazduMax(nonNull(criterion.getDepartureDateTo()) ? criterion.getDepartureDateTo().format(dateTimeFormatter) : null);
        payload.setTypyTransportu(ustawDomyslnyRodzajTransportu());
        payload.setWyzywienia(copyOf(translate(payload.getBoardTypeTranslation(), criterion.getBoardTypes())));
        payload.setKonfiguracja(utworzKonfiguracje(criterion));
        payload.setSortowanie(ustawDomyslneSortowanie());
        payload.setKategoriaHoteluMin(decimalFormatter.format(criterion.getMinHotelStandard()));
        payload.setKategoriaHoteluMax(decimalFormatter.format(5));
        payload.setCzyGrupowac(true);
        payload.setCzyCenaZaWszystkich(false);
        payload.setPaginacja(ustawPaginacje());
        payload.setCzyImprezaWeekendowa(false);
        return payload;
    }

    private ImmutableList<String> ustawDomyslnyRodzajTransportu() {
        log.debug("Way of transport set to default value: air");
        return ImmutableList.of("air");
    }

    private RainbowPayload.Paginacja ustawPaginacje() {
        //FIXME: do rozpykania
        int przeczytano = 0;
        int doSciagniecia = 18;
        return new RainbowPayload.Paginacja(
                String.valueOf(przeczytano),
                String.valueOf(doSciagniecia)
        );
    }

    private RainbowPayload.Sortowanie ustawDomyslneSortowanie() {
        return new RainbowPayload.Sortowanie(
                false,
                false,
                false,
                false,
                false
        );
    }

    private RainbowPayload.Konfiguracja utworzKonfiguracje(SearchCriterionDto searchCriterionDto) {
        return new RainbowPayload.Konfiguracja(ustawWiekPodroznych(searchCriterionDto), ustawLiczbePokoi());
    }

    private String ustawLiczbePokoi() {
        log.debug("No custom room qty selection available for Rainbow - room number set to default value: 1.");
        return String.valueOf(1);
    }

    private List<String> ustawWiekPodroznych(SearchCriterionDto searchCriterionDto) {
        return Stream.of(searchCriterionDto.getAdultsBirthDates(), searchCriterionDto.getChildrenBirthDates())
                .flatMap(Collection::stream)
                .map(birthDate -> birthDate.format(dateTimeFormatter))
                .sorted()
                .collect(toList());
    }

}
