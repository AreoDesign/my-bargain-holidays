package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class SearchCriterionDto implements Serializable {
    private static final long serialVersionUID = 2411963589406221772L;
    private Integer id;
    private AlertCriterionDto alertCriterion;
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
    private boolean isActive;
}
