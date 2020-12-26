//package com.areo.design.holidays.converter.impl.requestor;
//
//import com.areo.design.holidays.converter.EntityDtoConverter;
//import com.areo.design.holidays.dictionary.BoardType;
//import com.areo.design.holidays.dictionary.City;
//import com.areo.design.holidays.dictionary.Country;
//import com.areo.design.holidays.entity.requestor.RequestorEntity;
//import com.areo.design.holidays.entity.requestor.SearchCriterionEntity;
//import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import java.time.format.DateTimeFormatter;
//
//import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfEnumsAsString;
//import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfLocalDateAsString;
//import static com.areo.design.holidays.converter.EntityDtoConverter.stringAsCollectionOfEnum;
//import static com.areo.design.holidays.converter.EntityDtoConverter.stringAsCollectionOfLocalDate;
//import static java.util.Objects.isNull;
//
//@Component
//public class SearchCriterionConverter implements EntityDtoConverter<SearchCriterionEntity, SearchCriterion> {
//
//    private final DateTimeFormatter dateFormatter;
//
//    public SearchCriterionConverter(@Qualifier("dateFormatter") DateTimeFormatter dateFormatter) {
//        this.dateFormatter = dateFormatter;
//    }
//
//    @Override
//    public SearchCriterionEntity convertToEntity(SearchCriterion dto) {
//        return isNull(dto) ? null :
//                SearchCriterionEntity.builder()
//                        .id(dto.getId())
//                        .requestor(RequestorEntity.builder().id(dto.getRequestorId()).build())
//                        .childrenBirthDates(collectionOfLocalDateAsString(dateFormatter, dto.getChildrenBirthDates()))
//                        .adultsBirthDates(collectionOfLocalDateAsString(dateFormatter, dto.getAdultsBirthDates()))
//                        .departureDateFrom(dto.getDepartureDateFrom())
//                        .departureDateTo(dto.getDepartureDateTo())
//                        .stayLength(dto.getStayLength())
//                        .departureCities(collectionOfEnumsAsString(dto.getDepartureCities()))
//                        .boardTypes(collectionOfEnumsAsString(dto.getBoardTypes()))
//                        .countries(collectionOfEnumsAsString(dto.getCountries()))
//                        .minHotelStandard(dto.getMinHotelStandard())
//                        .creationTime(dto.getCreationTime())
//                        .active(dto.isActive())
//                        .build();
//    }
//
//    @Override
//    public SearchCriterion convertToDto(SearchCriterionEntity entity) {
//        return isNull(entity) ? null :
//                SearchCriterion.builder()
//                        .id(entity.getId())
//                        .requestorId(entity.getRequestor().getId())
//                        .childrenBirthDates(stringAsCollectionOfLocalDate(dateFormatter, entity.getChildrenBirthDates()))
//                        .adultsBirthDates(stringAsCollectionOfLocalDate(dateFormatter, entity.getAdultsBirthDates()))
//                        .departureDateFrom(entity.getDepartureDateFrom())
//                        .departureDateTo(entity.getDepartureDateTo())
//                        .stayLength(entity.getStayLength())
//                        .departureCities(stringAsCollectionOfEnum(City.class, entity.getDepartureCities()))
//                        .boardTypes(stringAsCollectionOfEnum(BoardType.class, entity.getBoardTypes()))
//                        .countries(stringAsCollectionOfEnum(Country.class, entity.getCountries()))
//                        .minHotelStandard(entity.getMinHotelStandard())
//                        .creationTime(entity.getCreationTime())
//                        .active(entity.isActive())
//                        .build();
//    }
//
//}
