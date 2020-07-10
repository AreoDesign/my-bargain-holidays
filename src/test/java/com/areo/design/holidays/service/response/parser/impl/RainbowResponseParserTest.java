package com.areo.design.holidays.service.response.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.component.parser.impl.RainbowResponseParser;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.HotelDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.Collections.EMPTY_MAP;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RainbowResponseParserTest {

    private static final String JSON_RELATIVE_PATH = "\\src\\test\\resources\\rainbow_response.json";

    private final Gson gson = new Gson();

    private final RainbowACLConverter rainbowACLConverter = new RainbowACLConverter(new RainbowTranslator());

    private final RainbowResponseParser rainbowResponseParser = new RainbowResponseParser(gson, rainbowACLConverter);

    private static Map<String, Object> readJson() {
        //read data from file
        Path individualPath = Paths.get("").toAbsolutePath();
        Path jsonFilePath = Paths.get(individualPath.toString().concat(JSON_RELATIVE_PATH));
        try {
            return new Gson().fromJson(Files.readString(jsonFilePath), Map.class);
        } catch (IOException e) {
            log.error("It was not possible to read from file: {}. Stacktrace: {}", jsonFilePath.toString(), e.getMessage());
            return EMPTY_MAP;
        }
    }

    @Test
    public void shouldParseJson_whenInputIsValid() {
        //given
        HttpEntity<Map<String, Object>> response = new HttpEntity<>(readJson());
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