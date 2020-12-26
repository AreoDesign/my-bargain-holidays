package com.areo.design.holidays.valueobjects.offer;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.valueobjects.atomic.HotelCode;
import com.areo.design.holidays.valueobjects.atomic.HotelName;
import com.areo.design.holidays.valueobjects.atomic.HotelOpinionScore;
import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class Hotel {
    HotelCode code;
    HotelName name;
    HotelStandard standard;
    HotelOpinionScore opinionScore;
    Country country;
    ImmutableList<Offer> offers;

    @Builder
    public Hotel(HotelCode code, HotelName name, HotelStandard standard, HotelOpinionScore opinionScore, Country country, List<Offer> offers) {
        this.code = code;
        this.name = name;
        this.standard = standard;
        this.opinionScore = opinionScore;
        this.country = country;
        this.offers = ImmutableList.copyOf(offers.stream()
                .distinct()
                .sorted(Comparator.comparing(offer -> offer.getPrice().getPriceInPLN()))
                .collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return "\nHotel(" +
                "country=" + country +
                ", \t\tstandard=" + standard +
                ", \t\topinionScore=" + opinionScore +
                ", \t\toffers=\n" + getOffersCustomizedToString() +
                ')';
    }

    private String getOffersCustomizedToString() {
        return offers.stream()
                .map(Offer::toString)
                .collect(Collectors.joining("\n"));
    }
}
