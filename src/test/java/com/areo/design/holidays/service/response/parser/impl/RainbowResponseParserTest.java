package com.areo.design.holidays.service.response.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.impl.RainbowResponseParser;
import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.offer.DetailDto;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.offer.OfferDto;
import com.areo.design.holidays.exception.ParsingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.areo.design.holidays.dictionary.BoardType.ALL_INCLUSIVE;
import static com.areo.design.holidays.dictionary.Country.GREECE;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RainbowResponseParserTest {

    public static final LocalDateTime TIMESTAMP = LocalDateTime.of(2020, 7, 30, 11, 52);
    private static final String SAMPLE_HOTEL_NAME = "Rethymno Village";
    private static final String SAMPLE_HOTEL_CODE = "HER80035";
    private static final Country SAMPLE_COUNTRY = GREECE;
    private static final double SAMPLE_OPINION_VALUE = 4.50d;
    private static final double SAMPLE_STANDARD_VALUE = 5d;
    private static final String SAMPLE_OFFER_CODE = "WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final String SAMPLE_OFFER_URL = "https://www.tui.pl/wypoczynek/grecja/kreta/rethymno-village-her80035/OfferCodeWS/WAWCHQ20201004185020201004202010110840L07HER80035DZX1AA02Ch01BD20190305";
    private static final BoardType SAMPLE_BOARD_TYPE = ALL_INCLUSIVE;
    private static final Integer SAMPLE_PRICE = 1589;
    private static final Integer SAMPLE_DURATION = 7;
    private static final LocalDateTime SAMPLE_DEPARTURE_TIME = LocalDateTime.of(2020, 10, 4, 18, 50);


    @Mock
    private RainbowACLConverter rainbowACLConverter;

    @Mock
    private Response response;

    @InjectMocks
    private RainbowResponseParser rainbowResponseParser;

    @Test
    public void shouldParseJson_whenInputIsValid() {
        //given
        when(response.getBody()).thenReturn(prepareBody(new RainbowResponseACL.Bloczek()));
        when(response.getTimestamp()).thenReturn(TIMESTAMP);
        when(rainbowACLConverter.convert(any(RainbowResponseACL.class))).thenReturn(returnHotelDtos());
        //when
        Collection<HotelDto> result = rainbowResponseParser.parse(response);
        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(1)
                .isInstanceOf(Collection.class)
                .extracting(HotelDto::getCountry)
                .containsOnlyOnce(Country.GREECE);
    }

    @Test
    public void shouldThrowException_whenResponseContainsNoElements() {
        //given
        when(response.getBody()).thenReturn(prepareBody());
        //when
        Collection<HotelDto> result = rainbowResponseParser.parse(response);
        //then
        assertThat(result).isEmpty();
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

    private RainbowResponseACL prepareBody(RainbowResponseACL.Bloczek... bloczek) {
        RainbowResponseACL rainbowResponseACL = new RainbowResponseACL();
        ReflectionTestUtils.setField(rainbowResponseACL, "Bloczki", List.of(bloczek));
        return rainbowResponseACL;
    }

    private HotelDto prepareHotel(Collection<OfferDto> offers) {
        return HotelDto.builder()
                .name(SAMPLE_HOTEL_NAME)
                .code(SAMPLE_HOTEL_CODE)
                .opinion(SAMPLE_OPINION_VALUE)
                .standard(SAMPLE_STANDARD_VALUE)
                .country(SAMPLE_COUNTRY)
                .offers(newHashSet(offers))
                .build();
    }

    private OfferDto prepareOffer(Collection<DetailDto> details) {
        return OfferDto.builder()
                .code(SAMPLE_OFFER_CODE)
                .url(SAMPLE_OFFER_URL)
                .departureTime(SAMPLE_DEPARTURE_TIME)
                .boardType(SAMPLE_BOARD_TYPE)
                .duration(SAMPLE_DURATION)
                .details(newHashSet(details))
                .build();
    }

    private Collection<HotelDto> returnHotelDtos() {
        return singletonList(prepareHotel(singleton(prepareOffer(singleton(prepareOfferDetail(TIMESTAMP))))));
    }

    private DetailDto prepareOfferDetail(LocalDateTime requestTime) {
        return DetailDto.builder()
                .requestTime(DetailDto.RequestTime.builder().responseHeaderTime(requestTime).build())
                .price(SAMPLE_PRICE)
                .build();
    }

}