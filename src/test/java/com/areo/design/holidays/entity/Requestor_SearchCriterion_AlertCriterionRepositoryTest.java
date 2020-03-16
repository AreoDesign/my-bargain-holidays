package com.areo.design.holidays.entity;

import com.areo.design.holidays.repository.AlertCriterionRepository;
import com.areo.design.holidays.repository.RequestorRepository;
import com.areo.design.holidays.repository.SearchCriterionRepository;
import lombok.extern.slf4j.Slf4j;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class Requestor_SearchCriterion_AlertCriterionRepositoryTest {

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
    @Order(10)
    void whenDeleteAllInBatch_thenEachRepositoryIsEmpty() {
        //when
        requestorRepository.deleteAllInBatch();
        searchCriterionRepository.deleteAllInBatch();
        alertCriterionRepository.deleteAllInBatch();
        //then
        assertThat(requestorRepository.findAll()).isEmpty();
        assertThat(searchCriterionRepository.findAll()).isEmpty();
        assertThat(alertCriterionRepository.findAll()).isEmpty();
    }

    private Requestor prepareRequestor() {
        return prepareRequestor("tester@test.pl");
    }

    private Requestor prepareRequestor(String login) {
        return Requestor.builder()
                .login(login)
                .password("tester")
                .build();
    }
}