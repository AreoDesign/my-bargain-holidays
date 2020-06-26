package com.areo.design.holidays.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

import static com.areo.design.holidays.dictionary.Technical.COMMA;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.emptyCollection;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public interface EntityDtoConverter<K, V> {

    K convertToEntity(V dto);

    V convertToDto(K entity);

    static <T extends Enum<T>> String collectionOfEnumsAsString(Set<T> enums) {
        return isNull(enums) ? EMPTY : enums.stream()
                .map(Enum::name)
                .collect(joining(COMMA.getValue()));
    }

    static <T extends Enum<T>> Set<T> stringAsCollectionOfEnum(Class<T> clazz, String flatCollection) {
        return isNull(flatCollection) ? emptySet() : Arrays.stream(flatCollection.split(COMMA.getValue()))
                .map(enumAsString -> Enum.valueOf(clazz, enumAsString.trim()))
                .collect(toSet());
    }

    static String collectionOfLocalDateAsString(DateTimeFormatter dateFormatter, Set<LocalDate> dates) {
        return isNull(dates) ? EMPTY : dates.stream()
                .map(date -> date.format(dateFormatter))
                .collect(joining(COMMA.getValue()));
    }

    static Set<LocalDate> stringAsCollectionOfLocalDate(DateTimeFormatter dateFormatter, String flatCollection) {
        return isNull(flatCollection) ? emptySet() : Arrays.stream(flatCollection.split(COMMA.getValue()))
                .map(dateAsString -> LocalDate.parse(dateAsString.trim(), dateFormatter))
                .collect(toSet());
    }

    default Collection<K> convertToEntities(Collection<V> dtos) {
        return isNull(dtos) ? emptyCollection() : dtos.stream()
                .map(this::convertToEntity)
                .collect(toCollection(LinkedList::new));
    }

    default Collection<V> convertToDtos(Collection<K> entities) {
        return isNull(entities) ? emptyCollection() : entities.stream()
                .map(this::convertToDto)
                .collect(toCollection(LinkedList::new));
    }

}
