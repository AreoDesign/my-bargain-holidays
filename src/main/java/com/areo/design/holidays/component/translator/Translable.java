package com.areo.design.holidays.component.translator;

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

public interface Translable {
    /**
     * need to translate for persistence read-write reasons
     *
     * @param dictionary
     * @param enums
     * @param <T>
     * @return
     */
    static <T extends Enum<T>> Collection<String> translate(Map<String, T> dictionary, Set<T> enums) {
        return isNull(enums) ? EMPTY_COLLECTION :
                dictionary.entrySet().stream()
                        .filter(entry -> enums.contains(entry.getValue()))
                        .map(Map.Entry::getKey)
                        .collect(toCollection(LinkedList::new));
    }

    Map<String, Country> getCountryTranslator();

    Map<String, BoardType> getBoardTypeTranslator();

    Map<String, City> getCityTranslator();

    Pair<Integer, Integer> getMinMaxStayLength();

}



