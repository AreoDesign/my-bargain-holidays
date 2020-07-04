package com.areo.design.holidays.service.response;

import com.areo.design.holidays.dictionary.TravelAgency;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.service.response.strategy.ResponseParserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ParserServiceDefault implements ParserService {

    private final ResponseParserProvider responseParserProvider;

    @Override
    public Collection<HotelDto> parse(@NotNull ResponseEntity<String> response) {
        //FIXME: get rid of custom header insertion
        Optional<String> travelAgencyHeader = requireNonNull(response.getHeaders().get(TravelAgency.getClassName()))
                .stream()
                .findFirst();
        TravelAgency travelAgency = travelAgencyHeader
                .map(TravelAgency::getByName)
                .orElseThrow(NoSuchElementException::new);
        return responseParserProvider.provide(travelAgency).parse(response);
    }

}
