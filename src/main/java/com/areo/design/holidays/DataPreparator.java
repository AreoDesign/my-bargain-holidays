package com.areo.design.holidays;

import com.areo.design.holidays.entity.AlertCriterionEntity;
import com.areo.design.holidays.entity.RequestorEntity;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import com.areo.design.holidays.repository.RequestorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.ITALY;
import static com.areo.design.holidays.dictionary.Country.PORTUGAL;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static com.areo.design.holidays.dictionary.Country.TURKEY;
import static com.areo.design.holidays.dictionary.Technical.COMMA;
import static java.lang.Math.toIntExact;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * THIS IS TEMPORARY CLASS TO SIMULATE DATABASE STATE
 * FIXME: NEED TO BE REMOVED ONCE LIQUIBASE IS READY AND HIBERNATE DDL-AUTO IS SWITCHED TO VALIDATE!
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataPreparator {

    private static final LocalDate BEGINNING_OF_SEPTEMBER = LocalDate.of(2020, 9, 1);
    private static final LocalDate MIDDLE_OF_OCTOBER = LocalDate.of(2020, 10, 20);
    private static final String MY_EMAIL = "arkadiusz.rogulski@gmail.com";
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    private static final String MY_BIRTH_DATE = LocalDate.of(1985, 1, 10).format(ofPattern(YEAR_MONTH_DAY));
    private static final String MY_WIFE_BIRTH_DATE = LocalDate.of(1988, 3, 10).format(ofPattern(YEAR_MONTH_DAY));
    private static final String MY_SON_BIRTH_DATE = LocalDate.of(2019, 3, 10).format(ofPattern(YEAR_MONTH_DAY));

    private final RequestorRepository requestorRepository;

    public void prepare() {
        RequestorEntity requestor = prepareRequestorWithSearchAndAlertCriterion();
        requestorRepository.save(requestor);
    }

    private RequestorEntity prepareRequestorWithSearchAndAlertCriterion() {
        RequestorEntity requestor = prepareRequestor();
        requestor.addSearchCriterion(prepareSearchCriterion(requestor));
        requestor.addAlertCriterion(prepareAlertCriterion(requestor));
        return requestor;
    }

    private RequestorEntity prepareRequestor() {
        return RequestorEntity.builder()
                .login(MY_EMAIL)
                .password("application-test")
                .build();
    }

    private SearchCriterionEntity prepareSearchCriterion(RequestorEntity requestor) {
        return SearchCriterionEntity.builder()
                .requestor(requestor)
                .adultsBirthDates(join(asList(MY_BIRTH_DATE, MY_WIFE_BIRTH_DATE), COMMA.getValue()))
                .childrenBirthDates(MY_SON_BIRTH_DATE)
                .departureDateFrom(BEGINNING_OF_SEPTEMBER)
                .departureDateTo(MIDDLE_OF_OCTOBER)
                .stayLength(toIntExact(Duration.ofDays(7).toDays()))
                .departureCities(join(singletonList(WARSAW.name()), COMMA.getValue()))
                .boardTypes(join(asList(ALL_INCLUSIVE, FULL_BOARD), COMMA.getValue()))
                .countries(join(asList(GREECE, CROATIA, SPAIN), COMMA.getValue()))
                .minHotelStandard(4d)
                .build();
    }

    private AlertCriterionEntity prepareAlertCriterion(RequestorEntity requestor) {
        return AlertCriterionEntity.builder()
                .requestor(requestor)
                .email(requestor.getLogin())
                .holidayStart(BEGINNING_OF_SEPTEMBER)
                .holidayEnd(MIDDLE_OF_OCTOBER)
                .countries(join(asList(GREECE, TURKEY, ITALY, SPAIN, PORTUGAL), COMMA.getValue()))
                .minHotelStandard(4d)
                .priceMax(2200)
                .build();
    }
}
