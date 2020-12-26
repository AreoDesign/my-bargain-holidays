package com.areo.design.holidays.valueobjects.requestor;

import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.valueobjects.atomic.Board;
import com.areo.design.holidays.valueobjects.atomic.CriterionState;
import com.areo.design.holidays.valueobjects.atomic.Date;
import com.areo.design.holidays.valueobjects.atomic.DateAndTime;
import com.areo.design.holidays.valueobjects.atomic.Duration;
import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchCriterion {
    ImmutableList<Date> childrenBirthDates;
    ImmutableList<Date> adultsBirthDates;
    Date departureDateFrom;
    Date departureDateTo;
    Duration stayLength;
    ImmutableSet<City> departureCities;
    ImmutableSet<Board> boards;
    ImmutableSet<Country> countries;
    HotelStandard minHotelStandard;
    DateAndTime creationTime;
    CriterionState state;
    ImmutableSet<AlertCriterion> alertCriteria;
}
