package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.RequestorEntity;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfEnumsAsString;
import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfLocalDateAsString;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Value
@Builder
public class SearchCriterionDto implements Serializable, EntityConvertible<SearchCriterionEntity> {
    private static final long serialVersionUID = 2411963589406221772L;
    private Integer id;
    private UUID requestorId;
    private Set<LocalDate> childrenBirthDates;
    private Set<LocalDate> adultsBirthDates;
    private LocalDate departureDateFrom;
    private LocalDate departureDateTo;
    private Integer stayLength;
    private Set<City> departureCities;
    private Set<BoardType> boardTypes;
    private Set<Country> countries;
    private Double minHotelStandard;
    private LocalDateTime creationTime;
    private boolean active;

    @Override
    public SearchCriterionEntity toEntity() {
        return SearchCriterionEntity.builder()
                .id(this.id)
                .requestor(RequestorEntity.builder().id(this.requestorId).build())
                .childrenBirthDates(collectionOfLocalDateAsString(ISO_LOCAL_DATE, this.childrenBirthDates))
                .adultsBirthDates(collectionOfLocalDateAsString(ISO_LOCAL_DATE, this.adultsBirthDates))
                .departureDateFrom(this.departureDateFrom)
                .departureDateTo(this.departureDateTo)
                .stayLength(this.stayLength)
                .departureCities(collectionOfEnumsAsString(this.departureCities))
                .boardTypes(collectionOfEnumsAsString(this.boardTypes))
                .countries(collectionOfEnumsAsString(this.countries))
                .minHotelStandard(this.minHotelStandard)
                .creationTime(this.creationTime)
                .active(this.active)
                .build();
    }

}
