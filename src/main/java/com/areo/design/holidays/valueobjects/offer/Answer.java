package com.areo.design.holidays.valueobjects.offer;

import com.areo.design.holidays.exception.NoPriceForOfferException;
import com.areo.design.holidays.valueobjects.complex.Price;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.negate;

@Value
public class Answer {

    ImmutableList<Hotel> hotelsWithTravelOffers;

    Comparator<Hotel> hotelCheapestOfferPriceAscComparator = (first, second) -> {
        Function<Hotel, Integer> minimumPriceFunc = hotel -> hotel.getOffers().stream()
                .map(Offer::getPrice)
                .map(Price::getPriceInPLN)
                .min(Integer::compareTo)
                .orElseThrow(NoPriceForOfferException::new);
        Integer firstHotelMinimumPriceOffer = minimumPriceFunc.apply(first);
        Integer secondHotelMinimumPriceOffer = minimumPriceFunc.apply(second);
        return Integer.compare(firstHotelMinimumPriceOffer, secondHotelMinimumPriceOffer);
    };

    @Builder
    public Answer(Collection<Hotel> hotelsWithTravelOffers) {
        this.hotelsWithTravelOffers = ImmutableList.copyOf(hotelsWithTravelOffers.stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    public static Answer of(Collection<Hotel> hotelsWithTravelOffers) {
        return new Answer(hotelsWithTravelOffers);
    }

    public boolean isEmpty() {
        return this.hotelsWithTravelOffers.isEmpty();
    }

    public boolean isNotEmpty() {
        return negate(this.hotelsWithTravelOffers.isEmpty());
    }

    @Override
    public String toString() {
        return "Answer(" +
                "hotelsWithTravelOffers=" + hotelsWithTravelOffers +
                ')';
    }

    public Answer sortedByPriceAscending() {
        Collection<Hotel> sortedHotelsWithOffers = Lists.newArrayList(this.getHotelsWithTravelOffers()).stream()
                .sorted(hotelCheapestOfferPriceAscComparator)
                .collect(Collectors.toList());
        return Answer.of(sortedHotelsWithOffers);
    }

}
