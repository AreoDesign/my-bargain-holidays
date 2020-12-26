package com.areo.design.holidays.valueobjects.complex;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Currency;

@EqualsAndHashCode
@Builder
@RequiredArgsConstructor(staticName = "of")
public class Price {
    private final static String POLISH_CURRENCY_ISO_4217_CODE = "PLN";

    private final Currency currency = Currency.getInstance(POLISH_CURRENCY_ISO_4217_CODE);

    @Min(value = 0, message = "price value must be positive")
    private final Integer plnValue; //value in PLN

    public Integer getPriceInPLN() {
        return plnValue;
    }

    public String getIsoCurrencyCodeAsString() {
        return currency.getCurrencyCode();
    }

    public String getLocalCurrencyCodeAsString() {
        return "zł";
    }

    @Override
    public String toString() {
        return "Price(" + plnValue + ' ' + currency + ')';
    }
}
