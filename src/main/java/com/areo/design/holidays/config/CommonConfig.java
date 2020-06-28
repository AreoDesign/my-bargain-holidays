package com.areo.design.holidays.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Configuration
public class CommonConfig {

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(30000))
                .setReadTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Bean
    @Qualifier("gson")
    public Gson gson() {
        return new GsonBuilder()
//TODO                .registerTypeAdapter(HotelRainbowACL.class, new RainbowJsonDeserializer())
                .create();
    }

    @Bean
    @Qualifier("dateFormatter")
    public DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Bean
    @Qualifier("dateTimeFormatter")
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    @Bean
    @Qualifier("doubleToStringOnePlaceAfterCommaFormatter")
    public DecimalFormat doubleToStringFormatter() {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df;
    }

}
