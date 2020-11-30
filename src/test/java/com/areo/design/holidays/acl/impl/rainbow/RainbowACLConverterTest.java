package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.valueobjects.offer.Detail;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import com.areo.design.holidays.valueobjects.offer.Offer;
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

    private static RainbowResponseBodyACL getJsonMappedToRainbowResponseACL() {
        return new Gson().fromJson(readRainbowJson(), RainbowResponseBodyACL.class);
    }

    @Test
    public void shouldConvertResponseACL_whenInputIsValid() {
        //given
        RainbowResponseBodyACL rainbowResponseBodyACL = getJsonMappedToRainbowResponseACL();
        when(rainbowTranslator.getBoardTypeTranslator()).thenCallRealMethod();
        when(rainbowTranslator.getDestinationTranslator()).thenCallRealMethod();
        //when
        Collection<Hotel> result = rainbowACLConverter.convert(rainbowResponseBodyACL);
        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(RAINBOW_TOURS.getOffersToDownload())
                .isInstanceOf(Collection.class)
                .extracting(Hotel::getCountry)
                .containsOnly(Country.GREECE, Country.SPAIN);
        assertThat(result)
                .flatExtracting(Hotel::getOffers)
                .flatExtracting(Offer::getDetails)
                .extracting(Detail::getRequestTime)
                .as("converter shall not add request time")
                .containsOnly(Detail.RequestTime.blank())
                .extracting(Detail.RequestTime::toLocalDateTime)
                .containsOnlyNulls();
    }

}