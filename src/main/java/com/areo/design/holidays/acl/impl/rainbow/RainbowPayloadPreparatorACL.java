package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.PayloadPreparatorACL;
import com.areo.design.holidays.component.translator.Translable;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.areo.design.holidays.component.translator.Translable.translate;
import static java.util.List.copyOf;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
public class RainbowPayloadPreparatorACL implements PayloadPreparatorACL<RainbowPayloadTemplateACL> {

    private final DateTimeFormatter dateTimeFormatter;
    private final DecimalFormat decimalFormatter;
    private final RainbowTranslator rainbowTranslator;

    @Override
    public RainbowPayloadTemplateACL prepare(SearchCriterionDto criterion) {
        RainbowPayloadTemplateACL payload = new RainbowPayloadTemplateACL();
        payload.setMiastaWyjazdu(copyOf(translate(getTranslator().getCityAirportCodeTranslator(), criterion.getDepartureCities())));
        payload.setPanstwa(copyOf(translate(getTranslator().getDestinationTranslator(), criterion.getCountries())));
        payload.setRegiony(null);//TODO: in RELEASE 2
        payload.setTerminWyjazduMin(criterion.getDepartureDateFrom().format(dateTimeFormatter));
        payload.setTerminWyjazduMax(nonNull(criterion.getDepartureDateTo()) ? criterion.getDepartureDateTo().format(dateTimeFormatter) : null);
        payload.setTypyTransportu(ustawDomyslnyRodzajTransportu());
        payload.setWyzywienia(copyOf(translate(getTranslator().getBoardTypeTranslator(), criterion.getBoardTypes())));
        payload.setKonfiguracja(utworzKonfiguracje(criterion));
        payload.setSortowanie(ustawDomyslneSortowanie());
        payload.setKategoriaHoteluMin(decimalFormatter.format(criterion.getMinHotelStandard()));
        payload.setKategoriaHoteluMax(decimalFormatter.format(5));
        payload.setCzyGrupowac(true);
        payload.setCzyCenaZaWszystkich(false);
        payload.setPaginacja(inicjalizujPaginacje());
        payload.setCzyImprezaWeekendowa(false);
        return payload;
    }

    @Override
    public Translable getTranslator() {
        return rainbowTranslator;
    }

    private ImmutableList<String> ustawDomyslnyRodzajTransportu() {
        log.debug("Way of transport set to default value: air");
        return ImmutableList.of("air");
    }

    private RainbowPayloadTemplateACL.Paginacja inicjalizujPaginacje() {
        return new RainbowPayloadTemplateACL.Paginacja(
                String.valueOf(0),
                String.valueOf(getDefaultOfferQuantityToDownload())
        );
    }

    private RainbowPayloadTemplateACL.Sortowanie ustawDomyslneSortowanie() {
        return new RainbowPayloadTemplateACL.Sortowanie(
                false,
                false,
                false,
                false,
                false
        );
    }

    private RainbowPayloadTemplateACL.Konfiguracja utworzKonfiguracje(SearchCriterionDto searchCriterionDto) {
        return new RainbowPayloadTemplateACL.Konfiguracja(ustawWiekPodroznych(searchCriterionDto), ustawLiczbePokoi());
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

    private Integer getDefaultOfferQuantityToDownload() {
        return TravelAgency.RAINBOW_TOURS.getOffersToDownload();
    }

}
