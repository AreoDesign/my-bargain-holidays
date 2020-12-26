package com.areo.design.holidays.dictionary;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Getter
public enum City {
    CRACOW("Kraków"),
    GDANSK("Gdańsk"),
    LODZ("Łódź"),
    MODLIN("Modlin"),
    WARSAW("Warszawa");

    private static final String MSG_TMPL = "No match for given entry (%s) found within known cities: %s";
    private final String inPolish;

    City(String inPolish) {
        this.inPolish = inPolish;
    }

    public static City getByPolishName(@NotBlank String name) {
        return Stream.of(City.values())
                .filter(city -> city.inPolish.equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(createMessage(name)));
    }

    private static String createMessage(String givenCity) {
        String knownCities = Stream.of(City.values())
                .map(city -> city.inPolish)
                .collect(joining(", "));
        return String.format(MSG_TMPL, givenCity, knownCities);
    }


    private String customizedName() {
        String name = this.name();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return "City(" + customizedName() + ')';
    }
}
