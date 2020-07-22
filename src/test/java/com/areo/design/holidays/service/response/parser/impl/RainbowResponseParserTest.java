package com.areo.design.holidays.service.response.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.areo.design.holidays.component.parser.impl.RainbowResponseParser;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.HotelDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static com.areo.design.holidays.service.response.parser.impl.BodyToPojoJsonMapperTest.readJson;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RainbowResponseParserTest {

    private final RainbowACLConverter rainbowACLConverter = new RainbowACLConverter(new RainbowTranslator());

    private final RainbowResponseParser rainbowResponseParser = new RainbowResponseParser(rainbowACLConverter);

    private static RainbowResponseACL getJsonMappedToRainbowResponseACL() {
        return new Gson().fromJson(readJson(), RainbowResponseACL.class);
    }

    @Test
    public void shouldParseJson_whenInputIsValid() {
        //given
        ResponseEntity<RainbowResponseACL> response = new ResponseEntity<>(getJsonMappedToRainbowResponseACL(), HttpStatus.OK);
        //when
        Collection<HotelDto> result = rainbowResponseParser.parse(response);
        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(RAINBOW_TOURS.getOffersToDownload())
                .isInstanceOf(Collection.class)
                .extracting(HotelDto::getCountry)
                .containsOnly(Country.GREECE, Country.SPAIN);
    }

}