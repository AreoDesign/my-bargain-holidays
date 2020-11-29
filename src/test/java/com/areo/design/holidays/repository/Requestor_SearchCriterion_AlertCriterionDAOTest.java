package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.requestor.AlertCriterionEntity;
import com.areo.design.holidays.entity.requestor.RequestorEntity;
import com.areo.design.holidays.entity.requestor.SearchCriterionEntity;
import com.areo.design.holidays.repository.dao.AlertCriterionDAO;
import com.areo.design.holidays.repository.dao.RequestorDAO;
import com.areo.design.holidays.repository.dao.SearchCriterionDAO;
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

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.BoardType.FULL_BOARD;
import static com.areo.design.holidays.dictionary.City.WARSAW;
import static com.areo.design.holidays.dictionary.Country.CROATIA;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.areo.design.holidays.dictionary.Country.SPAIN;
import static com.areo.design.holidays.dictionary.Technical.COMMA;
import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.join;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Requestor_SearchCriterion_AlertCriterionDAOTest {

    private static final String LOGIN = "tester@test.pl";
    private final RequestorDAO requestorDAO;
    private final SearchCriterionDAO searchCriterionDAO;
    private final AlertCriterionDAO alertCriterionDAO;

    @Autowired
    public Requestor_SearchCriterion_AlertCriterionDAOTest(RequestorDAO requestorDAO,
                                                           SearchCriterionDAO searchCriterionDAO,
                                                           AlertCriterionDAO alertCriterionDAO) {
        this.requestorDAO = requestorDAO;
        this.searchCriterionDAO = searchCriterionDAO;
        this.alertCriterionDAO = alertCriterionDAO;
    }

    @Test
    @Order(1)
    void whenEnteringTest_thenRepositoriesAreEmpty() {
        assertThat(requestorDAO.findAll()).isEmpty();
        assertThat(searchCriterionDAO.findAll()).isEmpty();
        assertThat(alertCriterionDAO.findAll()).isEmpty();
    }

    @Test
    @Order(2)
    void whenSavedRequestorWithWrongInput_thenShoudThrowException() {
        //given
        RequestorEntity requestor = prepareRequestor("tester");
        //when, expect
        assertThatThrownBy(() -> requestorDAO.save(requestor))
                .hasRootCauseExactlyInstanceOf(ConstraintViolationException.class)
                .hasStackTraceContaining("login must be valid email address.");
    }

    @Test
    @Order(3)
    void whenSavedRequestor_thenRequestorPersistedToDB() {
        //given
        RequestorEntity requestor = prepareRequestor();
        //when
        requestorDAO.save(requestor);
        //then
        assertThat(requestor.getId()).isNotNull();
        assertThat(requestorDAO.findById(requestor.getId()).orElseThrow(EntityNotFoundException::new))
                .hasNoNullFieldsOrProperties()
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .isEqualToIgnoringGivenFields(requestor, "searchCriteria", "alertCriteria");
        assertThat(requestorDAO.findAll())
                .hasSize(1);
        assertThat(searchCriterionDAO.findAll())
                .isEmpty();
        assertThat(alertCriterionDAO.findAll())
                .isEmpty();
    }

    @Test
    @Order(4)
    void whenRequestorSavedWithCriterion_thenCriterionPersistedOnCascade() {
        //given
        RequestorEntity requestor = requestorDAO.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.addSearchCriterion(prepareSearchCriterion());
        //when
        requestor = requestorDAO.save(requestor);
        //then
        assertThat(searchCriterionDAO.findAll())
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
        RequestorEntity requestor = requestorDAO.findByLogin(LOGIN)
                .orElseThrow(EntityNotFoundException::new);
        SearchCriterionEntity foundSearchCriterion = searchCriterionDAO.findAllByRequestorId(requestor.getId()).stream()
                .findFirst().orElseThrow(EntityNotFoundException::new);
        //when
        LocalDateTime creationTime = foundSearchCriterion.getCreationTime();
        foundSearchCriterion.setStayLength(14);
        searchCriterionDAO.save(foundSearchCriterion);
        //then
        assertThat(searchCriterionDAO.findAll())
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
        RequestorEntity requestor = requestorDAO.findByLogin(LOGIN).orElseThrow(EntityNotFoundException::new);
        requestor.addAlertCriterion(prepareAlertCriterion());
        //when
        requestor = requestorDAO.save(requestor);
        //then
        assertThat(alertCriterionDAO.findAll())
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
        requestor = requestorDAO.save(requestor);
        //then
        assertThat(requestorDAO.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "searchCriteria", "alertCriteria")
                .contains(requestor)
                .flatExtracting(RequestorEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .contains(requestor.getCreationTime());
        assertThat(searchCriterionDAO.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "requestor")
                .contains(searchCriterion)
                .flatExtracting(SearchCriterionEntity::getCreationTime)
                .describedAs("creation time is compared after rounding to second precision")
                .usingComparatorForType(Comparator.comparing(o -> o.truncatedTo(ChronoUnit.SECONDS)), LocalDateTime.class)
                .contains(searchCriterion.getCreationTime());
        assertThat(alertCriterionDAO.findAll())
                .hasSize(2)
                .usingElementComparatorIgnoringFields("creationTime", "requestor")
                .contains(alertCriterion);
    }

    @Test
    @Order(8)
    void whenDeleteAllInBatch_thenEachRepositoryIsEmpty() {
        //when
        alertCriterionDAO.deleteAllInBatch();
        searchCriterionDAO.deleteAllInBatch();
        requestorDAO.deleteAllInBatch();
        //then
        assertThat(requestorDAO.findAll()).isEmpty();
        assertThat(searchCriterionDAO.findAll()).isEmpty();
        assertThat(alertCriterionDAO.findAll()).isEmpty();
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
                .adultsBirthDates(LocalDate.of(1985, 1, 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .departureDateFrom(LocalDate.now().plusMonths(1))
                .departureDateTo(LocalDate.now().plusMonths(2))
                .stayLength(toIntExact(Duration.ofDays(7).toDays()))
                .departureCities(join(singletonList(WARSAW.name()), COMMA.getValue()))
                .boardTypes(join(asList(ALL_INCLUSIVE, FULL_BOARD), COMMA.getValue()))
                .countries(join(asList(GREECE, CROATIA, SPAIN), COMMA.getValue()))
                .minHotelStandard(4d)
                .active(true)
                .build();
    }

    private AlertCriterionEntity prepareAlertCriterion() {
        return AlertCriterionEntity.builder()
                .email(LOGIN)
                .holidayStart(LocalDate.now().plusMonths(1).plusDays(2))
                .holidayEnd(LocalDate.now().plusMonths(1).minusDays(10))
                .countries(join(singletonList(GREECE), COMMA.getValue()))
                .minHotelStandard(3d)
                .priceMax(1000)
                .build();
    }
}