package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.valueobjects.atomic.Board;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import com.areo.design.holidays.valueobjects.offer.Offer;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static com.areo.design.holidays.service.response.parser.impl.BodyToPojoJsonMapperTest.readRainbowJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RainbowACLConverterTest {

    @Mock
    private RainbowTranslator rainbowTranslator;

    @InjectMocks
    private RainbowACLConverter rainbowACLConverter;

    private static RainbowResponseBodyACL getJsonMappedToRainbowResponseACL() {
        return new Gson().fromJson(readRainbowJson(), RainbowResponseBodyACL.class);
    }

    @Test
    public void shouldConvertResponseACL_whenInputIsValid() {
        //given
        RainbowResponse rainbowResponse = RainbowResponse.builder()
                .bodyACL(getJsonMappedToRainbowResponseACL())
                .statusCode(HttpStatus.OK)
                .timestamp(LocalDateTime.of(2020, 12, 21, 15, 48))
                .build();
        //and
        when(rainbowTranslator.getBoardTypeTranslator()).thenCallRealMethod();
        //amd
        when(rainbowTranslator.getCountryTranslator()).thenCallRealMethod();

        //when
        Collection<Hotel> result = rainbowACLConverter.convert(rainbowResponse);

        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(RAINBOW_TOURS.getOffersToDownload())
                .isInstanceOf(Collection.class)
                .extracting(Hotel::getCountry)
                .containsOnly(Country.GREECE, Country.SPAIN);
        assertThat(result)
                .flatExtracting(Hotel::getOffers)
                .extracting(Offer::getBoard)
                .contains(Board.of(BoardType.ALL_INCLUSIVE));
    }

}