package com.areo.design.holidays.service;

import com.areo.design.holidays.dto.AlertCriterionDto;
import com.areo.design.holidays.dto.RequestorDto;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.google.common.collect.ImmutableSet;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.ITALY;
import static com.areo.design.holidays.dictionary.Country.PORTUGAL;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static com.areo.design.holidays.dictionary.Country.TURKEY;
import static java.lang.Math.toIntExact;

/**
 * THIS IS TEMPORARY CLASS TO STUB USER DATA
 * FIXME: NEED TO BE REMOVED ONCE ALL DATA TAKEN FROM DATABASE!
 */
public class StarterDataProvider {

    private static final LocalDate BEGINNING_OF_SEPTEMBER = LocalDate.of(2020, 9, 1);
    private static final LocalDate MIDDLE_OF_OCTOBER = LocalDate.of(2020, 10, 20);
    private static final String LOGIN = "arkadiusz.rogulski@gmail.com";
    private static final LocalDate MY_BIRTH_DATE = LocalDate.of(1985, 1, 10);
    private static final LocalDate MY_WIFE_BIRTH_DATE = LocalDate.of(1988, 3, 10);
    private static final LocalDate MY_SON_BIRTH_DATE = LocalDate.of(2019, 3, 10);


    public static RequestorDto prepareRequestorStub() {
        return RequestorDto.builder()
                .id(null)
                .login(LOGIN)
                .password("application-test")
                .searchCriteria(ImmutableSet.of(prepareSearchCriterionStub()))
                .alertCriteria(ImmutableSet.of(prepareAlertCriterionStub()))
                .active(true)
                .build();
    }

    public static SearchCriterionDto prepareSearchCriterionStub() {
        return SearchCriterionDto.builder()
                .id(null)
                .requestorId(null)
                .adultsBirthDates(ImmutableSet.of(MY_BIRTH_DATE, MY_WIFE_BIRTH_DATE))
                .childrenBirthDates(ImmutableSet.of(MY_SON_BIRTH_DATE))
                .departureDateFrom(BEGINNING_OF_SEPTEMBER)
                .departureDateTo(MIDDLE_OF_OCTOBER)
                .stayLength(toIntExact(Duration.ofDays(7).toDays()))
                .departureCities(ImmutableSet.of(WARSAW))
                .boardTypes(ImmutableSet.of(ALL_INCLUSIVE, FULL_BOARD))
                .countries(ImmutableSet.of(GREECE, CROATIA, SPAIN))
                .minHotelStandard(4d)
                .active(true)
                .creationTime(LocalDateTime.now())
                .build();
    }

    public static AlertCriterionDto prepareAlertCriterionStub() {
        return AlertCriterionDto.builder()
                .id(null)
                .requestorId(null)
                .email(LOGIN)
                .holidayStart(BEGINNING_OF_SEPTEMBER)
                .holidayEnd(MIDDLE_OF_OCTOBER)
                .countries(ImmutableSet.of(GREECE, TURKEY, ITALY, SPAIN, PORTUGAL))
                .minHotelStandard(4d)
                .priceMax(2200)
                .active(true)
                .build();
    }
}
