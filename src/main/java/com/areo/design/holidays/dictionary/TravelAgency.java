package com.areo.design.holidays.dictionary;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Getter
public enum TravelAgency {
    RAINBOW_TOURS("https://rpl-api.r.pl/wyszukiwarka/api/wyszukaj", //former api URL: 'https://rpl-api.r.pl/szukaj/api/wyszukaj'
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            18),
    TUI("https://www.tui.pl/search/offers",
            DateTimeFormatter.ofPattern("dd.MM.yyyy'T'HH:mm"),
            null);

    private static final String MSG_TMPL = "No match for given entry (%s) found within known Travel Agencies: %s";

    private final URI uri;
    private final DateTimeFormatter dateTimeFormatter;
    private final Integer offersToDownload;

    TravelAgency(String url, DateTimeFormatter dateTimeFormatter, Integer offersToDownload) {
        this.uri = URI.create(url);
        this.dateTimeFormatter = dateTimeFormatter;
        this.offersToDownload = offersToDownload;
    }

    public static String getClassName() {
        return TravelAgency.class.getSimpleName();
    }

    public static TravelAgency getByName(@NotBlank String travelAgency) {
        return Stream.of(TravelAgency.values())
                .filter(agency -> agency.name().equals(travelAgency))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(createMessage(travelAgency)));
    }

    private static String createMessage(String travelAgency) {
        return String.format(MSG_TMPL, travelAgency, Stream.of(TravelAgency.values()).map(Enum::name));
    }
}
