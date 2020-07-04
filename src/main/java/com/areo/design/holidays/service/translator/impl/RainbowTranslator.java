package com.areo.design.holidays.service.translator.impl;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.service.translator.Translable;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.BED_AND_BREAKFAST;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.BoardType.HALF_BOARD;
import static com.areo.design.holidays.dictionary.BoardType.OVER_NIGHT;
import static com.areo.design.holidays.dictionary.City.CRACOW;
import static com.areo.design.holidays.dictionary.City.GDANSK;
import static com.areo.design.holidays.dictionary.City.LODZ;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.BULGARIA;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.CYPRUS;
import static com.areo.design.holidays.dictionary.Country.EGYPT;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.ITALY;
import static com.areo.design.holidays.dictionary.Country.MONTENEGRO;
import static com.areo.design.holidays.dictionary.Country.MOROCCO;
import static com.areo.design.holidays.dictionary.Country.PORTUGAL;
import static com.areo.design.holidays.dictionary.Country.ROMANIA;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static com.areo.design.holidays.dictionary.Country.TURKEY;
import static java.util.Map.entry;

@Component
public class RainbowTranslator implements Translable {

    @Override
    public Map<String, Country> getDestinationTranslator() {
        return Map.ofEntries(
                entry("bulgaria", BULGARIA),
                entry("chorwacja", CROATIA),
                entry("cypr", CYPRUS),
                entry("cypr-polnocny", CYPRUS),
                entry("egipt", EGYPT),
                entry("grecja", GREECE),
                entry("wlochy", ITALY),
                entry("maroko", MOROCCO),
                entry("czarnogora", MONTENEGRO),
                entry("portugalia", PORTUGAL),
                entry("rumunia", ROMANIA),
                entry("hiszpania", SPAIN),
                entry("turcja", TURKEY)
        );
    }

    @Override
    public Map<String, BoardType> getBoardTypeTranslator() {
        return Map.ofEntries(
                entry("all-inclusive", ALL_INCLUSIVE),
                entry("3-posilki", FULL_BOARD),
                entry("2-posilki", HALF_BOARD),
                entry("sniadania", BED_AND_BREAKFAST),
                entry("bez-wyzywienia", OVER_NIGHT)
        );
    }

    @Override
    public Map<String, City> getCityAirportCodeTranslator() {
        return Map.ofEntries(
                entry("krakow", CRACOW),
                entry("gdansk", GDANSK),
                entry("lodz", LODZ),
                entry("warszawa", WARSAW)
        );
    }

    @Override
    public Pair<Integer, Integer> getMinMaxStayLength() {
        return Pair.of(1, 22);
    }
}
