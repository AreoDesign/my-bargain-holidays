package com.areo.design.holidays.converter;

import com.areo.design.holidays.dictionary.Technical;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityDtoConverter<K, V> {

    K convertToEntity(V dto);

    V convertToDto(K entity);

    default Collection<K> convertToEntities(Collection<V> dtos) {
        return dtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default Collection<V> convertToDtos(Collection<K> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    default <T extends Enum<T>> String collectionOfEnumsAsString(Set<T> enums) {
        return enums.stream()
                .map(Enum::name)
                .collect(Collectors.joining(Technical.COMMA.getValue()));
    }

    default <T extends Enum<T>> Set<T> stringAsCollectionOfEnum(Class<T> clazz, String flatCollection) {
        return Arrays.stream(StringUtils.split(flatCollection, Technical.COMMA.getValue()))
                .map(enumAsString -> Enum.valueOf(clazz, enumAsString))
                .collect(Collectors.toSet());
    }

    default String collectionOfLocalDateAsString(DateTimeFormatter dateFormatter, Set<LocalDate> dates) {
        return dates.stream()
                .map(date -> date.format(dateFormatter))
                .collect(Collectors.joining(Technical.COMMA.getValue()));
    }

    default Set<LocalDate> stringAsCollectionOfLocalDate(DateTimeFormatter dateFormatter, String flatCollection) {
        return Arrays.stream(StringUtils.split(flatCollection, Technical.COMMA.getValue()))
                .map(dateAsString -> LocalDate.parse(dateAsString, dateFormatter))
                .collect(Collectors.toSet());
    }
}
