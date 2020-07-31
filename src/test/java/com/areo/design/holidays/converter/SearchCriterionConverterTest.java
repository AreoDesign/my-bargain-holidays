package com.areo.design.holidays.converter;

import com.areo.design.holidays.converter.impl.SearchCriterionConverter;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;
import com.areo.design.holidays.entity.RequestorEntity;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.City.MODLIN;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static java.lang.Math.toIntExact;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchCriterionConverterTest {

    private static final LocalDate ADULT_BIRTH_DATE = LocalDate.of(1985, 1, 1);
    private static final LocalDate DEPARTURE_DATE_FROM = LocalDate.of(2020, 3, 29);
    private static final int STAY_LENGTH = toIntExact(Duration.ofDays(7).toDays());
    private static final UUID REQESTOR_ID = UUID.randomUUID();
    private static final Integer CRITERION_ID = 987;
    private static final double MIN_HOTEL_STANDARD = 4d;

    private SearchCriterionConverter converter = new SearchCriterionConverter(DateTimeFormatter.ISO_LOCAL_DATE);

    @Test
    public void whenConvertEntityToDto_thenCorrect() {
        //given
        SearchCriterionEntity entity = prepareSearchCriterionEntity();
        //when
        SearchCriterionDto dto = converter.convertToDto(entity);
        //then
        assertThat(dto.getId()).isEqualTo(CRITERION_ID);
        assertThat(dto.getRequestorId()).isEqualTo(REQESTOR_ID);
        assertThat(dto.getChildrenBirthDates()).isEmpty();
        assertThat(dto.getAdultsBirthDates()).containsExactlyInAnyOrder(ADULT_BIRTH_DATE);
        assertThat(dto.getDepartureDateFrom()).isEqualTo(DEPARTURE_DATE_FROM);
        assertThat(dto.getDepartureDateTo()).isEqualTo(DEPARTURE_DATE_FROM.plusMonths(1));
        assertThat(dto.getStayLength()).isEqualTo(STAY_LENGTH);
        assertThat(dto.getDepartureCities()).containsExactlyInAnyOrder(WARSAW, MODLIN);
        assertThat(dto.getBoardTypes()).containsExactlyInAnyOrder(ALL_INCLUSIVE, FULL_BOARD);
        assertThat(dto.getCountries()).containsExactlyInAnyOrder(GREECE, CROATIA, SPAIN);
        assertThat(dto.getMinHotelStandard()).isEqualTo(4d);
    }

    @Test
    public void whenConvertDtoToEntity_thenCorrect() {
        //given
        SearchCriterionDto dto = prepareSearchCriterionDto();
        //when
        SearchCriterionEntity entity = converter.convertToEntity(dto);
        //then
        assertThat(entity.getId()).isEqualTo(CRITERION_ID);
        assertThat(entity.getRequestor().getId()).isEqualTo(REQESTOR_ID);
        assertThat(entity.getChildrenBirthDates()).isEmpty();
        assertThat(entity.getAdultsBirthDates()).isEqualTo("1985-01-01");
        assertThat(entity.getDepartureDateFrom()).isEqualTo(DEPARTURE_DATE_FROM);
        assertThat(entity.getDepartureDateTo()).isEqualTo(DEPARTURE_DATE_FROM.plusMonths(1));
        assertThat(entity.getStayLength()).isEqualTo(STAY_LENGTH);
        assertThat(entity.getDepartureCities()).contains("WARSAW", "MODLIN");
        assertThat(entity.getBoardTypes()).contains("ALL_INCLUSIVE", "FULL_BOARD");
        assertThat(entity.getCountries()).contains("SPAIN", "CROATIA", "GREECE");
        assertThat(entity.getMinHotelStandard()).isEqualTo(MIN_HOTEL_STANDARD);
    }

    private SearchCriterionEntity prepareSearchCriterionEntity() {
        return SearchCriterionEntity.builder()
                .id(CRITERION_ID)
                .requestor(RequestorEntity.builder().id(REQESTOR_ID).build())
                .adultsBirthDates("1985-01-01")
                .departureDateFrom(DEPARTURE_DATE_FROM)
                .departureDateTo(DEPARTURE_DATE_FROM.plusMonths(1))
                .stayLength(STAY_LENGTH)
                .departureCities("WARSAW, MODLIN")
                .boardTypes("ALL_INCLUSIVE, FULL_BOARD")
                .countries("GREECE, CROATIA, SPAIN")
                .minHotelStandard(MIN_HOTEL_STANDARD)
                .build();
    }

    private SearchCriterionDto prepareSearchCriterionDto() {
        return SearchCriterionDto.builder()
                .id(CRITERION_ID)
                .requestorId(REQESTOR_ID)
                .adultsBirthDates(Set.of(ADULT_BIRTH_DATE))
                .departureDateFrom(DEPARTURE_DATE_FROM)
                .departureDateTo(DEPARTURE_DATE_FROM.plusMonths(1))
                .stayLength(STAY_LENGTH)
                .departureCities(Set.of(WARSAW, MODLIN))
                .boardTypes(Set.of(ALL_INCLUSIVE, FULL_BOARD))
                .countries(Set.of(GREECE, CROATIA, SPAIN))
                .minHotelStandard(MIN_HOTEL_STANDARD)
                .build();
    }

}