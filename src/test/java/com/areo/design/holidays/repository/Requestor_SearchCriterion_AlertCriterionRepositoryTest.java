package com.areo.design.holidays.repository;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.AlertCriterionEntity;
import com.areo.design.holidays.entity.RequestorEntity;
import com.areo.design.holidays.entity.SearchCriterionEntity;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

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
        RequestorEntity requestor = prepareRequestor("tester");
        //when, expect
        assertThatThrownBy(() -> requestorRepository.save(requestor))
                .hasRootCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("login must be valid email address.");
    }

    @Test
    @Order(3)
    void whenSavedRequestor_thenRequestorPersistedToDB() {
        //given
        RequestorEntity requestor = prepareRequestor();
        //when
        requestorRepository.save(requestor);
        //then
        assertThat(requestor.getId()).isNotNull();
        assertThat(requestorRepository.findById(requestor.getId()).orElseThrow(EntityNotFoundException::new))
                .hasNoNullFieldsOrProperties()
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .isEqualToIgnoringGivenFields(requestor, "searchCriteria", "alertCriteria");
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
        RequestorEntity requestor = requestorRepository.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.addSearchCriterion(prepareSearchCriterion());
        //when
        requestor = requestorRepository.save(requestor);
        //then
        assertThat(searchCriterionRepository.findAll())
                .describedAs("verify saved search criterion is the only record in table, with expected values")
                .hasSize(1)
                .describedAs("exclusions: creationTime - due to: time rounding error, requestor - due to exclusion on @Equals due to circular reference issue.")
                .usingElementComparatorIgnoringFields("creationTime", "requestor")
                .containsExactlyElementsOf(requestor.getSearchCriteria())
                .flatExtracting(SearchCriterionEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .containsExactly(requestor.getSearchCriteria().stream()
                        .findFirst()
                        .map(SearchCriterionEntity::getCreationTime)
                        .get());
    }

    @Test
    @Order(5)
    void whenCriterionUpdated_thenCriterionCreationTimeNotChanged() {
        //given
        RequestorEntity requestor = requestorRepository.findByLogin(LOGIN)
                .orElseThrow(EntityNotFoundException::new);
        SearchCriterionEntity foundSearchCriterion = searchCriterionRepository.findAllByRequestorId(requestor.getId()).stream()
                .findFirst().orElseThrow(EntityNotFoundException::new);
        //when
        LocalDateTime creationTime = foundSearchCriterion.getCreationTime();
        foundSearchCriterion.setStayLength(14);
        searchCriterionRepository.save(foundSearchCriterion);
        //then
        assertThat(searchCriterionRepository.findAll())
                .hasSize(1)
                .flatExtracting(SearchCriterionEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .containsExactly(creationTime);
    }

    @Test
    @Order(6)
    void whenRequestorSavedWithAlert_thenAlertPersistedOnCascade() {
        //given
        RequestorEntity requestor = requestorRepository.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.addAlertCriterion(prepareAlertCriterion());
        //when
        requestor = requestorRepository.save(requestor);
        //then
        assertThat(alertCriterionRepository.findAll())
                .hasSize(1)
                .containsExactly(requestor.getAlertCriteria().stream().findAny().get());
    }

    @Test
    @Order(7)
    void whenRequestorSavedWithRelatedEntities_thenAllPersistedOnCascade() {
        //given
        RequestorEntity requestor = prepareRequestor("developer@test.pl");
        SearchCriterionEntity searchCriterion = prepareSearchCriterion();
        AlertCriterionEntity alertCriterion = prepareAlertCriterion();
        requestor.addSearchCriterion(searchCriterion);
        requestor.addAlertCriterion(alertCriterion);
        //when
        requestor = requestorRepository.save(requestor);
        //then
        assertThat(requestorRepository.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "searchCriteria", "alertCriteria")
                .contains(requestor)
                .flatExtracting(RequestorEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class);
        assertThat(searchCriterionRepository.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "requestor")
                .contains(searchCriterion)
                .flatExtracting(SearchCriterionEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class);
        assertThat(alertCriterionRepository.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "requestor")
                .contains(alertCriterion);
    }

    @Test
    @Order(8)
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

    private RequestorEntity prepareRequestor() {
        return prepareRequestor(LOGIN);
    }

    private RequestorEntity prepareRequestor(String login) {
        return RequestorEntity.builder()
                .login(login)
                .password(login)
                .build();
    }

    private SearchCriterionEntity prepareSearchCriterion() {
        return SearchCriterionEntity.builder()
                .adultsBirthDates(LocalDate.of(1985, 1, 1).format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .departureDateFrom(LocalDate.now().plusMonths(1))
                .departureDateTo(LocalDate.now().plusMonths(2))
                .stayLength(toIntExact(Duration.ofDays(7).toDays()))
                .departureCities(StringUtils.join(singletonList(City.WARSAW.name())))
                .boardTypes(StringUtils.join(asList(BoardType.ALL_INCLUSIVE, BoardType.FULL_BOARD)))
                .countries(StringUtils.join(asList(Country.GREECE, Country.CROATIA, Country.SPAIN)))
                .minHotelStandard(4d)
                .active(true)
                .build();
    }

    private AlertCriterionEntity prepareAlertCriterion() {
        return AlertCriterionEntity.builder()
                .email(LOGIN)
                .holidayStart(LocalDate.now().plusMonths(1).plusDays(2))
                .holidayEnd(LocalDate.now().plusMonths(1).minusDays(10))
                .countries(StringUtils.join(singletonList(Country.GREECE)))
                .minHotelStandard(3d)
                .priceMax(1000)
                .build();
    }
}