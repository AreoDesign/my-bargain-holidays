package com.areo.design.holidays.service.response.parser.impl;

import com.areo.design.holidays.acl.impl.rainbow.RainbowResponseACL;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BodyToPojoJsonMapperTest {

    private static final String JSON_RELATIVE_PATH = "\\src\\test\\resources\\rainbow_response.json";

    static String readJson() {
        //read data from file
        Path individualPath = Paths.get("").toAbsolutePath();
        Path jsonFilePath = Paths.get(individualPath.toString().concat(JSON_RELATIVE_PATH));
        try {
            return Files.readString(jsonFilePath);
        } catch (IOException e) {
            log.error("It was not possible to read from file: {}. Stacktrace: {}", jsonFilePath.toString(), e.getMessage());
            return null;
        }
    }

    @Test
    void shouldMapJsonToGivenPojoClass() {
        //given
        Gson gson = new Gson();
        //when
        RainbowResponseACL result = gson.fromJson(readJson(), RainbowResponseACL.class);
        //then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(RainbowResponseACL.class)
                .hasNoNullFieldsOrPropertiesExcept("timestamp");
    }
}
