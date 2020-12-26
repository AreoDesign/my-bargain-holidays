package com.areo.design.holidays.service.response.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.component.parser.impl.RainbowResponseParser;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.exception.ParsingException;
import com.areo.design.holidays.valueobjects.offer.Answer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RainbowResponseParserTest {

    @Mock
    private RainbowACLConverter rainbowACLConverter;

    @Mock
    private RainbowResponse response;

    @InjectMocks
    private RainbowResponseParser rainbowResponseParser;

    @Test
    public void shouldCallRainbowACLConverterToParseResponseBodyJson() {
        //when
        Answer answer = rainbowResponseParser.parse(response);
        //then
        assertThat(answer)
                .extracting(Answer::getHotelsWithTravelOffers)
                .isInstanceOf(Collection.class);
        verify(rainbowACLConverter.convert(eq(response)), only());
    }

    @Test
    public void shouldThrowException_whenResponseBodyContainsNoBody() {
        //when
        Throwable throwable = catchThrowable(() -> rainbowResponseParser.parse(response));
        //then
        assertThat(throwable)
                .isExactlyInstanceOf(ParsingException.class)
                .hasMessage("Response has no body to parse.");
    }

}