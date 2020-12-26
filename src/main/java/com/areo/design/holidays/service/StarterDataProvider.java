package com.areo.design.holidays.service;

import com.areo.design.holidays.dictionary.State;
import com.areo.design.holidays.valueobjects.atomic.Board;
import com.areo.design.holidays.valueobjects.atomic.CriterionState;
import com.areo.design.holidays.valueobjects.atomic.Date;
import com.areo.design.holidays.valueobjects.atomic.DateAndTime;
import com.areo.design.holidays.valueobjects.atomic.Duration;
import com.areo.design.holidays.valueobjects.atomic.Email;
import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import com.areo.design.holidays.valueobjects.atomic.Password;
import com.areo.design.holidays.valueobjects.atomic.RequestorState;
import com.areo.design.holidays.valueobjects.complex.Price;
import com.areo.design.holidays.valueobjects.requestor.AlertCriterion;
import com.areo.design.holidays.valueobjects.requestor.Requestor;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

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

/**
 * THIS IS TEMPORARY CLASS TO STUB USER DATA
 * FIXME: NEED TO BE REMOVED ONCE ALL DATA TAKEN FROM DATABASE!
 */
public class StarterDataProvider {

    private static final LocalDate BEGINNING_OF_SEPTEMBER = LocalDate.of(LocalDate.now().getYear(), 9, 1);
    private static final LocalDate MIDDLE_OF_OCTOBER = LocalDate.of(LocalDate.now().getYear(), 10, 20);
    private static final String LOGIN = "arkadiusz.rogulski@gmail.com";
    private static final LocalDate MY_BIRTH_DATE = LocalDate.of(1985, 1, 10);
    private static final LocalDate MY_WIFE_BIRTH_DATE = LocalDate.of(1988, 3, 10);
    private static final LocalDate MY_SON_BIRTH_DATE = LocalDate.of(2019, 3, 10);


    public static Requestor prepareRequestorStub() {
        return Requestor.builder()
                .login(Email.of(LOGIN))
                .password(Password.of("application-test"))
                .searchCriteria(ImmutableSet.of(prepareSearchCriterionStub()))
                .state(RequestorState.of(State.ACTIVE))
                .build();
    }

    public static SearchCriterion prepareSearchCriterionStub() {
        return SearchCriterion.builder()
                .adultsBirthDates(ImmutableList.of(Date.of(MY_BIRTH_DATE), Date.of(MY_WIFE_BIRTH_DATE)))
                .childrenBirthDates(ImmutableList.of(Date.of(MY_SON_BIRTH_DATE)))
                .departureDateFrom(Date.of(BEGINNING_OF_SEPTEMBER))
                .departureDateTo(Date.of(MIDDLE_OF_OCTOBER))
                .stayLength(Duration.builder().days(7).build())
                .departureCities(ImmutableSet.of(WARSAW))
                .boards(ImmutableSet.of(Board.of(ALL_INCLUSIVE), Board.of(FULL_BOARD)))
                .countries(ImmutableSet.of(GREECE, CROATIA, SPAIN))
                .minHotelStandard(HotelStandard.of(4d))
                .state(CriterionState.of(com.areo.design.holidays.dictionary.State.ACTIVE))
                .creationTime(DateAndTime.of(LocalDateTime.now()))
                .alertCriteria(ImmutableSet.of(prepareAlertCriterionStub()))
                .build();
    }

    public static AlertCriterion prepareAlertCriterionStub() {
        return AlertCriterion.builder()
                .email(Email.of(LOGIN))
                .holidayStart(Date.of(BEGINNING_OF_SEPTEMBER))
                .holidayEnd(Date.of(MIDDLE_OF_OCTOBER))
                .countries(ImmutableSet.of(GREECE, TURKEY, ITALY, SPAIN, PORTUGAL))
                .minHotelStandard(HotelStandard.of(4d))
                .priceMax(Price.of(2200))
                .state(CriterionState.of(State.ACTIVE))
                .build();
    }
}
