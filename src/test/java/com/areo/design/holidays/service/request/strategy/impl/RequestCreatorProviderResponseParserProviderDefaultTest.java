package com.areo.design.holidays.service.request.strategy.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static com.areo.design.holidays.dictionary.TravelAgency.TUI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestCreatorProviderResponseParserProviderDefaultTest {

    @Mock
    private RainbowRequestCreator rainbowRequestCreator;

    @InjectMocks
    private RequestCreatorProviderDefault requestProviderDefault;

    @BeforeMethod
    public void init() {
        initMocks(this);
        requestProviderDefault.setCreatorsByTravelAgency(Map.of(RAINBOW_TOURS, rainbowRequestCreator));
    }

    @Test
    public void whenRequestedKnownTravelAgency_thenProvideCorrectCreator() {
        assertThat(requestProviderDefault.provide(RAINBOW_TOURS))
                .as("verification if strategy provides correct creator")
                .isExactlyInstanceOf(RainbowRequestCreator.class);
    }

    @Test
    public void whenRequestedUnKnownTravelAgency_thenThrowException() {
        //when
        Throwable result = catchThrowable(() -> requestProviderDefault.provide(TUI));
        //expect
        assertThat(result)
                .as("verification if strategy throws exception when given travel agency out of strategy")
                .isExactlyInstanceOf(NotImplementedException.class)
                .hasMessage("No creator found for given agency: TUI");
    }
}