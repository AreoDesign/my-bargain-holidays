package com.areo.design.holidays.converter;

import com.areo.design.holidays.converter.impl.SearchCriterionConverter;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import org.apache.commons.lang3.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchCriterionConverterTest {

    public static final LocalDate ADULT_BIRTH_DATE = LocalDate.of(1985, 1, 1);
    public static final LocalDate DEPARTURE_DATE_FROM = LocalDate.of(2020, 3, 29);
    public static final int STAY_LENGTH = toIntExact(Duration.ofDays(7).toDays());
    public static final List<String> CITIES = singletonList(WARSAW.name());
    public static final List<BoardType> BOARD_TYPES = asList(ALL_INCLUSIVE, FULL_BOARD);
    public static final List<Country> COUNTRIES = asList(GREECE, CROATIA, SPAIN);

    @Mock
    @Qualifier("dateFormatter")
    private DateTimeFormatter dateFormatter;

    @InjectMocks
    private SearchCriterionConverter converter;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenConvertEntityToDto_thenCorrect() {
        //given
        SearchCriterionEntity entity = prepareSearchCriterion();
        //when
        SearchCriterionDto dto = converter.convertToDto(entity);
        //then
        assertThat(dto.getId()).isNull();
        assertThat(dto.getChildrenBirthDates()).isEmpty();
        assertThat(dto.getAdultsBirthDates()).containsExactly(ADULT_BIRTH_DATE);
        assertThat(dto.getDepartureDateFrom()).isEqualTo(DEPARTURE_DATE_FROM.plusMonths(1));
        assertThat(dto.getDepartureDateTo()).isNull();
        assertThat(dto.getStayLength()).isEqualTo(STAY_LENGTH);
        assertThat(dto.getDepartureCities()).containsExactly(WARSAW);
        assertThat(dto.getBoardTypes()).containsExactly(ALL_INCLUSIVE, FULL_BOARD);
        assertThat(dto.getCountries()).containsExactly(GREECE, CROATIA, SPAIN);
        assertThat(dto.getMinHotelStandard()).isEqualTo(4d);
    }

    private SearchCriterionEntity prepareSearchCriterion() {
        return SearchCriterionEntity.builder()
                .adultsBirthDates(ADULT_BIRTH_DATE.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .departureDateFrom(DEPARTURE_DATE_FROM)
                .departureDateTo(DEPARTURE_DATE_FROM.plusMonths(1))
                .stayLength(STAY_LENGTH)
                .departureCities(StringUtils.join(CITIES))
                .boardTypes(StringUtils.join(BOARD_TYPES))
                .countries(StringUtils.join(COUNTRIES))
                .minHotelStandard(4d)
                .build();
    }

}