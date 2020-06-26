package com.areo.design.holidays.dictionary;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Getter
public enum TravelAgency {
    RAINBOW_TOURS("https://rpl-api.r.pl/wyszukiwarka/api/wyszukaj", "yyyy-MM-dd'T'HH:mm:ss'Z'", 18), //former api URL: 'https://rpl-api.r.pl/szukaj/api/wyszukaj'
    TUI("https://www.tui.pl/search/offers", "dd.MM.yyyy'T'HH:mm", null);

    private final URI uri;
    private final String dateTimeFormat;
    private final Integer offersToDownload;

    TravelAgency(String url, String dateTimeFormat, Integer offersToDownload) {
        this.uri = URI.create(url);
        this.dateTimeFormat = dateTimeFormat;
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
        final String template = "No match for given entry (%s) found within known Travel Agencies: %s";
        return String.format(template, travelAgency, Stream.of(TravelAgency.values()).map(Enum::name));
    }
}
