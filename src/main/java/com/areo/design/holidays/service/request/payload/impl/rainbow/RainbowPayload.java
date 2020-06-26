package com.areo.design.holidays.service.request.payload.impl.rainbow;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.service.request.payload.Payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
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
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Map.entry;

@Data
public class RainbowPayload implements Payload {
    private List<String> miastaWyjazdu;
    private List<String> panstwa;
    private List<String> regiony; //TODO: use regions in RELEASE 2
    private String terminWyjazduMin;
    private String terminWyjazduMax;
    private List<String> typyTransportu;
    private List<String> wyzywienia;
    private Konfiguracja konfiguracja;
    private Sortowanie sortowanie;
    private String kategoriaHoteluMin;
    private String kategoriaHoteluMax;
    private boolean czyGrupowac;
    private boolean czyCenaZaWszystkich;
    private Paginacja paginacja;
    private boolean czyImprezaWeekendowa;

    @Override
    public Map<Country, Collection<String>> getDestinationTranslator() {
        return Map.ofEntries(
                entry(BULGARIA, singletonList("bulgaria")),
                entry(CROATIA, singletonList("chorwacja")),
                entry(CYPRUS, asList("cypr", "cypr-polnocny")),
                entry(EGYPT, singletonList("egipt")),
                entry(GREECE, singletonList("grecja")),
                entry(ITALY, singletonList("wlochy")),
                entry(MOROCCO, singletonList("maroko")),
                entry(MONTENEGRO, singletonList("czarnogora")),
                entry(PORTUGAL, singletonList("portugalia")),
                entry(ROMANIA, singletonList("rumunia")),
                entry(SPAIN, singletonList("hiszpania")),
                entry(TURKEY, singletonList("turcja"))
        );
    }

    @Override
    public Map<BoardType, String> getBoardTypeTranslator() {
        return Map.ofEntries(
                entry(ALL_INCLUSIVE, "all-inclusive"),
                entry(FULL_BOARD, "3-posilki"),
                entry(HALF_BOARD, "2-posilki"),
                entry(BED_AND_BREAKFAST, "sniadania"),
                entry(OVER_NIGHT, "bez-wyzywienia")
        );
    }

    @Override
    public Map<City, String> getCityAirportCodeTranslator() {
        return Map.ofEntries(
                entry(CRACOW, "krakow"),
                entry(GDANSK, "gdansk"),
                entry(LODZ, "lodz"),
                entry(WARSAW, "warszawa")
        );
    }

    @Override
    public Pair<Integer, Integer> getMinMaxStayLength() {
        return Pair.of(1, 22);
    }

    @Data
    @AllArgsConstructor
    public static class Konfiguracja {
        private List<String> wiek;
        private String liczbaPokoi;
    }

    @Data
    @AllArgsConstructor
    public static class Paginacja {
        private String przeczytane;
        private String iloscDoPobrania;
    }

    @Data
    @AllArgsConstructor
    public static class Sortowanie {
        private boolean czyPoDacie;
        private boolean czyPoCenie;
        private boolean czyPoOcenach;
        private boolean czyPoPolecanych;
        private boolean czyDesc;
    }
}
