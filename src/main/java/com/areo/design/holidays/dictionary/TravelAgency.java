package com.areo.design.holidays.dictionary;

import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Getter
public enum TravelAgency {
    RAINBOW_TOURS("https://rpl-api.r.pl/wyszukiwarka/api/wyszukaj", //former api URL: 'https://rpl-api.r.pl/szukaj/api/wyszukaj'
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            18,
            HotelStandard.of(5d)),
    TUI("https://www.tui.pl/search/offers",
            DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"),
            null,
            null); //FIXME: need to implement!

    private static final String MSG_TMPL = "No match for given entry (%s) found within known travel agencies: %s";

    private final URI uri;
    private final DateTimeFormatter dateTimeFormatter;
    private final Integer offersToDownload;
    private final HotelStandard maxOfferedHotelStandard;

    TravelAgency(String url, DateTimeFormatter dateTimeFormatter, Integer offersToDownload, HotelStandard maxHotelStandard) {
        this.uri = URI.create(url);
        this.dateTimeFormatter = dateTimeFormatter;
        this.offersToDownload = offersToDownload;
        this.maxOfferedHotelStandard = maxHotelStandard;
    }

    public static String getClassName() {
        return TravelAgency.class.getSimpleName();
    }

    public static TravelAgency getByName(@NotBlank String travelAgency) {
        return Stream.of(TravelAgency.values())
                .filter(agency -> agency.name().equalsIgnoreCase(travelAgency))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(createMessage(travelAgency)));
    }

    private static String createMessage(String givenTravelAgency) {
        String knownAgencies = Stream.of(TravelAgency.values())
                .map(Enum::name)
                .collect(joining(", "));
        return String.format(MSG_TMPL, givenTravelAgency, knownAgencies);
    }
}
