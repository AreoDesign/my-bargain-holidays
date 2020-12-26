package com.areo.design.holidays.valueobjects.offer;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.valueobjects.atomic.Board;
import com.areo.design.holidays.valueobjects.atomic.Duration;
import com.areo.design.holidays.valueobjects.atomic.OfferCode;
import com.areo.design.holidays.valueobjects.atomic.ResponseTime;
import com.areo.design.holidays.valueobjects.atomic.Url;
import com.areo.design.holidays.valueobjects.complex.Departure;
import com.areo.design.holidays.valueobjects.complex.Price;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Offer {
    TravelAgency travelAgency;
    OfferCode code;
    Url url;
    Departure departure;
    Board board;
    Duration duration;
    Price price;
    ResponseTime responseTime;

    @Override
    public String toString() {
        return "\tOffer(" +
                "price=" + price +
                ", \tdeparture=" + departure +
                ", \tboard=" + board +
                ", \tduration=" + duration +
                ", \turl=" + url +
                ')';
    }
}
