package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.offer.DetailDto;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.offer.OfferDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private static RainbowResponseACL getJsonMappedToRainbowResponseACL() {
        return new Gson().fromJson(readRainbowJson(), RainbowResponseACL.class);
    }

    @Test
    public void shouldConvertResponseACL_whenInputIsValid() {
        //given
        RainbowResponseACL rainbowResponseACL = getJsonMappedToRainbowResponseACL();
        when(rainbowTranslator.getBoardTypeTranslator()).thenCallRealMethod();
        when(rainbowTranslator.getDestinationTranslator()).thenCallRealMethod();
        //when
        Collection<HotelDto> result = rainbowACLConverter.convert(rainbowResponseACL);
        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(RAINBOW_TOURS.getOffersToDownload())
                .isInstanceOf(Collection.class)
                .extracting(HotelDto::getCountry)
                .containsOnly(Country.GREECE, Country.SPAIN);
        assertThat(result)
                .flatExtracting(HotelDto::getOffers)
                .flatExtracting(OfferDto::getDetails)
                .extracting(DetailDto::getRequestTime)
                .containsOnlyNulls(); //does not convert request time from body
    }

}