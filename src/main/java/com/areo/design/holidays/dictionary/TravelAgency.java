package com.areo.design.holidays.dictionary;

import lombok.Getter;

import java.net.URI;

@Getter
public enum TravelAgency {
    RAINBOW_TOURS("https://rpl-api.r.pl/szukaj/api/wyszukaj"),
    TUI("https://www.tui.pl/search/offers");

    private URI uri;

    TravelAgency(String url) {
        this.uri = URI.create(url);
    }
}
