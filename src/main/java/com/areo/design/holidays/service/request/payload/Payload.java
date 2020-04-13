package com.areo.design.holidays.service.request.payload;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toCollection;
import static org.apache.commons.collections.CollectionUtils.EMPTY_COLLECTION;

/**
 * generic interface for carrying metadata in request
 */
public interface Payload {
    static <T extends Enum<T>> Collection<String> translateComplex(Map<T, Collection<String>> dictionary, Set<T> enums) {
        return isNull(enums) ? EMPTY_COLLECTION :
                dictionary.entrySet().stream()
                        .filter(entry -> enums.contains(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .flatMap(Collection::stream)
                        .collect(toCollection(LinkedList::new));
    }

    static <T extends Enum<T>> Collection<String> translate(Map<T, String> dictionary, Set<T> enums) {
        return isNull(enums) ? EMPTY_COLLECTION :
                dictionary.entrySet().stream()
                        .filter(entry -> enums.contains(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .collect(toCollection(LinkedList::new));
    }

    Map<Country, Collection<String>> getDestinationTranslation();

    Map<BoardType, String> getBoardTypeTranslation();

    Map<City, String> getCityAirportCodeTranslation();

    Pair<Integer, Integer> getMinMaxStayLength();

}
