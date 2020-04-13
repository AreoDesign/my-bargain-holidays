package com.areo.design.holidays.dictionary;

import lombok.Getter;

import java.net.URI;

@Getter
public enum TravelAgency {
    RAINBOW_TOURS("https://rpl-api.r.pl/wyszukiwarka/api/wyszukaj", "yyyy-MM-dd'T'HH:mm:ss'Z'"), //former api URL: 'https://rpl-api.r.pl/szukaj/api/wyszukaj'
    TUI("https://www.tui.pl/search/offers", "dd.MM.yyyy'T'HH:mm");

    private URI uri;
    private String dateTimeFormat;

    TravelAgency(String url, String dateTimeFormat) {
        this.uri = URI.create(url);
        this.dateTimeFormat = dateTimeFormat;
    }
}
