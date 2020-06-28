package com.areo.design.holidays.service.request.creator.impl.rainbow;

import com.areo.design.holidays.dto.SearchCriterionDto;
import com.areo.design.holidays.service.request.creator.HttpEntityCreator;
import com.areo.design.holidays.service.request.payload.PayloadPreparator;
import com.areo.design.holidays.service.request.payload.impl.rainbow.RainbowPayload;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class RainbowHttpEntityCreator implements HttpEntityCreator {

    private final Gson gson;
    private final PayloadPreparator<RainbowPayload> payloadPreparator;

    public RainbowHttpEntityCreator(@Qualifier("gson") Gson gson,
                                    @Qualifier("rainbowPayloadPreparator") PayloadPreparator<RainbowPayload> payloadPreparator) {
        this.gson = gson;
        this.payloadPreparator = payloadPreparator;
    }

    @Override
    public HttpEntity<String> create(SearchCriterionDto searchCriterionDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(newArrayList(MediaType.APPLICATION_JSON));
        RainbowPayload payload = payloadPreparator.prepare(searchCriterionDto);
        return new HttpEntity<>(gson.toJson(payload), headers);
    }

    @Override
    public HttpEntity<String> createNext(HttpEntity<String> httpEntity) {
        RainbowPayload incomingPayload = gson.fromJson(httpEntity.getBody(), RainbowPayload.class);
        RainbowPayload modifiedPayload = payloadPreparator.prepareNext(incomingPayload);
        return new HttpEntity<>(gson.toJson(modifiedPayload), httpEntity.getHeaders());
    }

}