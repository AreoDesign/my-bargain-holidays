package com.areo.design.holidays.service.response.strategy.impl;

import com.areo.design.holidays.dto.HotelDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class RainbowResponseParserTest {

    private static final String JSON_RELATIVE_PATH = "\\src\\test\\resources\\rainbow_response.json";

    private RainbowResponseParser rainbowResponseParser = new RainbowResponseParser(new Gson());

    private static String readJson() {
        //read data from file
        Path individualPath = Paths.get("").toAbsolutePath();
        Path jsonFilePath = Paths.get(individualPath.toString().concat(JSON_RELATIVE_PATH));
        try {
            return Files.readString(jsonFilePath);
        } catch (IOException e) {
            log.error("It was not possible to read from file: {}. Stacktrace: {}", jsonFilePath.toString(), e.getMessage());
            return StringUtils.EMPTY;
        }

    }

    @Test
    public void shouldParseJson_whenInputIsValid() {
        //given
        HttpEntity<String> response = prepareHttpEntityResponse();
        //when
        Collection<HotelDto> result = rainbowResponseParser.parse(response);
        //then
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .isInstanceOf(Collection.class);
    }

    private HttpEntity<String> prepareHttpEntityResponse() {
        String body = readJson();
        return new HttpEntity<>(body);
    }
}