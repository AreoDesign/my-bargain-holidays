package com.areo.design.holidays.config.collector;

import com.areo.design.holidays.acl.impl.rainbow.RainbowACLConverter;
import com.areo.design.holidays.acl.impl.rainbow.RainbowPayloadPreparatorACL;
import com.areo.design.holidays.component.parser.ResponseParser;
import com.areo.design.holidays.component.parser.impl.RainbowResponseParser;
import com.areo.design.holidays.component.request.Request;
import com.areo.design.holidays.component.request.httpentity.impl.RainbowRequestEntityCreator;
import com.areo.design.holidays.component.request.impl.RainbowRequest;
import com.areo.design.holidays.component.request.sender.RequestSender;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.service.collector.OfferCollectorService;
import com.areo.design.holidays.service.collector.impl.RainbowOfferCollectorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;

@Configuration
public class RainbowCollectorServiceConfig implements CollectorService {

    private final DateTimeFormatter dateTimeFormatter;
    private final DecimalFormat decimalFormatter;
    private final RequestSender requestSender;

    public RainbowCollectorServiceConfig(@Qualifier("dateFormatter") DateTimeFormatter dateTimeFormatter,
                                         @Qualifier("doubleToStringOnePlaceAfterCommaFormatter") DecimalFormat decimalFormatter,
                                         @Qualifier("requestSenderDefault") RequestSender requestSender) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormatter = decimalFormatter;
        this.requestSender = requestSender;
    }

    @Override
    public TravelAgency getDedicatedTravelAgency() {
        return RAINBOW_TOURS;
    }

    @Override
    public OfferCollectorService getOfferCollectorService() {
        return rainbowOfferCollectorService();
    }

    @Bean
    OfferCollectorService rainbowOfferCollectorService() {
        return new RainbowOfferCollectorService(rainbowRequest(), requestSender, rainbowResponseParser());
    }

    @Bean
    Request rainbowRequest() {
        return new RainbowRequest(rainbowHttpEntityCreator());
    }

    @Bean
    RainbowRequestEntityCreator rainbowHttpEntityCreator() {
        return new RainbowRequestEntityCreator(rainbowPayloadPreparator());
    }

    @Bean
    RainbowPayloadPreparatorACL rainbowPayloadPreparator() {
        return new RainbowPayloadPreparatorACL(dateTimeFormatter, decimalFormatter, rainbowTranslator());
    }

    @Bean
    RainbowTranslator rainbowTranslator() {
        return new RainbowTranslator();
    }

    @Bean
    ResponseParser rainbowResponseParser() {
        return new RainbowResponseParser(rainbowACLConverter());
    }

    @Bean
    RainbowACLConverter rainbowACLConverter() {
        return new RainbowACLConverter(rainbowTranslator());
    }

}
