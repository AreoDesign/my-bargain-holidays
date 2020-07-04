package com.areo.design.holidays.service.request.strategy.impl;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.request.strategy.RequestCreator;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static com.areo.design.holidays.dictionary.TravelAgency.TUI;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestCreatorProviderDefaultTest {

    @Mock
    private RainbowRequestCreator rainbowRequestCreator;

    @Mock
    private Map<TravelAgency, RequestCreator> creatorsByTravelAgency;

    @InjectMocks
    private RequestCreatorProviderDefault requestProviderDefault;

    @Before
    public void initialSettings() {
        //given
        when(creatorsByTravelAgency.containsKey(RAINBOW_TOURS)).thenReturn(true);
        //and
        when(creatorsByTravelAgency.get(RAINBOW_TOURS)).thenReturn(rainbowRequestCreator);
    }

    @Test
    public void whenRequestedKnownTravelAgency_thenProvideCorrectCreator() {
        //given
        when(creatorsByTravelAgency.containsKey(RAINBOW_TOURS)).thenReturn(true);
        //and
        when(creatorsByTravelAgency.get(RAINBOW_TOURS)).thenReturn(rainbowRequestCreator);
        //expect
        assertThat(requestProviderDefault.provide(RAINBOW_TOURS))
                .as("verification if strategy provides correct creator")
                .isExactlyInstanceOf(RainbowRequestCreator.class);
        verifyNoMoreInteractions(rainbowRequestCreator, creatorsByTravelAgency);
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
        verifyNoMoreInteractions(rainbowRequestCreator, creatorsByTravelAgency);
    }
}