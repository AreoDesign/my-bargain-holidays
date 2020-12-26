package com.areo.design.holidays.valueobjects.requestor;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.valueobjects.atomic.CriterionState;
import com.areo.design.holidays.valueobjects.atomic.Date;
import com.areo.design.holidays.valueobjects.atomic.Email;
import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import com.areo.design.holidays.valueobjects.complex.Price;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertCriterion {
    Email email;
    Date holidayStart;
    Date holidayEnd;
    ImmutableSet<Country> countries;
    Price priceMax;
    HotelStandard minHotelStandard;
    CriterionState state;
}
