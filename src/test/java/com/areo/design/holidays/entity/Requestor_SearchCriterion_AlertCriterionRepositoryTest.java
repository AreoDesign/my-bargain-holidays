package com.areo.design.holidays.entity;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.repository.AlertCriterionRepository;
import com.areo.design.holidays.repository.RequestorRepository;
import com.areo.design.holidays.repository.SearchCriterionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class Requestor_SearchCriterion_AlertCriterionRepositoryTest {

    private static final String LOGIN = "tester@test.pl";
    private final RequestorRepository requestorRepository;
    private final SearchCriterionRepository searchCriterionRepository;
    private final AlertCriterionRepository alertCriterionRepository;

    @Autowired
    public Requestor_SearchCriterion_AlertCriterionRepositoryTest(RequestorRepository requestorRepository,
                                                                  SearchCriterionRepository searchCriterionRepository,
                                                                  AlertCriterionRepository alertCriterionRepository) {
        this.requestorRepository = requestorRepository;
        this.searchCriterionRepository = searchCriterionRepository;
        this.alertCriterionRepository = alertCriterionRepository;
    }

    @Test
    @Order(1)
    void whenEnteringTest_thenRepositoriesAreEmpty() {
        assertThat(requestorRepository.findAll()).isEmpty();
        assertThat(searchCriterionRepository.findAll()).isEmpty();
        assertThat(alertCriterionRepository.findAll()).isEmpty();
    }

    @Test
    @Order(2)
    void whenSavedRequestorWithWrongInput_thenShoudThrowException() {
        //given
        Requestor requestor = prepareRequestor("tester");
        //when, expect
        assertThatThrownBy(()-> requestorRepository.save(requestor))
                .hasRootCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("login must be valid email address.");
    }

    @Test
    @Order(3)
    void whenSavedRequestor_thenRequestorPersistedToDB() {
        //given
        Requestor requestor = prepareRequestor();
        //when
        requestorRepository.save(requestor);
        //then
        assertThat(requestor.getId()).isNotNull();
        assertThat(requestorRepository.findById(requestor.getId()).orElseThrow(EntityNotFoundException::new))
                .hasNoNullFieldsOrProperties()
                .isEqualTo(requestor);
        assertThat(requestorRepository.findAll())
                .hasSize(1);
        assertThat(searchCriterionRepository.findAll())
                .isEmpty();
        assertThat(alertCriterionRepository.findAll())
                .isEmpty();
    }

    @Test
    @Order(4)
    void whenRequestorSavedWithCriterion_thenCriterionPersistedOnCascade() {
        //given
        Requestor requestor = requestorRepository.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.addSearchCriterion(prepareSearchCriterion());
        //when
        requestor = requestorRepository.save(requestor);
        //then
        assertThat(searchCriterionRepository.findAll())
                .hasSize(1)
                .containsExactlyElementsOf(requestor.getSearchCriteria());
    }

    @Test
    @Order(5)
    void whenRequestorSavedWithCriterionAndAlert_thenCriterionAndAlertPersistedOnCascade() {
        //given
        Requestor requestor = requestorRepository.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.getSearchCriteria().stream()
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .addAlertCriterion(prepareAlertCriterion());
        //when
        requestor = requestorRepository.save(requestor);
        //then
        assertThat(alertCriterionRepository.findAll())
                .hasSize(1)
                .containsExactly(requestor.getSearchCriteria().stream().findAny().get().getAlertCriterion());
    }

    @Test
    @Order(6)
    void whenDeleteAllInBatch_thenEachRepositoryIsEmpty() {
        //when
        alertCriterionRepository.deleteAllInBatch();
        searchCriterionRepository.deleteAllInBatch();
        requestorRepository.deleteAllInBatch();
        //then
        assertThat(requestorRepository.findAll()).isEmpty();
        assertThat(searchCriterionRepository.findAll()).isEmpty();
        assertThat(alertCriterionRepository.findAll()).isEmpty();
    }

    private Requestor prepareRequestor() {
        return prepareRequestor(LOGIN);
    }

    private Requestor prepareRequestor(String login) {
        return Requestor.builder()
                .login(login)
                .password("tester")
                .build();
    }

    private SearchCriterion prepareSearchCriterion() {
        return SearchCriterion.builder()
                .adultsBirthDates(LocalDate.of(1985, 1, 1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .departureDateFrom(LocalDate.now().plusMonths(1))
                .departureDateTo(LocalDate.now().plusMonths(2))
                .stayLength(toIntExact(Duration.ofDays(7).toDays()))
                .departureCities(StringUtils.join(singletonList(City.WARSAW.name())))
                .boardTypes(StringUtils.join(asList(BoardType.ALL_INCLUSIVE, BoardType.FULL_BOARD)))
                .countries(StringUtils.join(asList(Country.GREECE, Country.CROATIA, Country.SPAIN)))
                .minHotelStandard(4d)
                .build();
    }

    private AlertCriterion prepareAlertCriterion() {
        return AlertCriterion.builder()
                .email(LOGIN)
                .holidayStart(LocalDate.now().plusMonths(1).plusDays(2))
                .holidayEnd(LocalDate.now().plusMonths(1).minusDays(10))
                .countries(StringUtils.join(singletonList(Country.GREECE)))
                .minHotelStandard(3d)
                .priceMax(1000)
                .build();
    }
}