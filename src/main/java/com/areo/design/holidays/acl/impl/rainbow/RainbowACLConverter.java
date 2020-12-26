package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.ACLConverter;
import com.areo.design.holidays.acl.ResponseBodyACL;
import com.areo.design.holidays.component.response.impl.RainbowResponse;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.City;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.exception.ParsingException;
import com.areo.design.holidays.exception.TranslationException;
import com.areo.design.holidays.exception.UnrecognizedBoardTypeException;
import com.areo.design.holidays.exception.UnrecognizedCountryException;
import com.areo.design.holidays.valueobjects.atomic.Board;
import com.areo.design.holidays.valueobjects.atomic.DateAndTime;
import com.areo.design.holidays.valueobjects.atomic.Duration;
import com.areo.design.holidays.valueobjects.atomic.HotelCode;
import com.areo.design.holidays.valueobjects.atomic.HotelName;
import com.areo.design.holidays.valueobjects.atomic.HotelOpinionScore;
import com.areo.design.holidays.valueobjects.atomic.HotelStandard;
import com.areo.design.holidays.valueobjects.atomic.OfferCode;
import com.areo.design.holidays.valueobjects.atomic.ResponseTime;
import com.areo.design.holidays.valueobjects.atomic.Url;
import com.areo.design.holidays.valueobjects.complex.Departure;
import com.areo.design.holidays.valueobjects.complex.Price;
import com.areo.design.holidays.valueobjects.offer.Hotel;
import com.areo.design.holidays.valueobjects.offer.Offer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.BooleanUtils.negate;

@Slf4j
@RequiredArgsConstructor
public class RainbowACLConverter implements ACLConverter<RainbowResponse> {

    private final RainbowTranslator rainbowTranslator;

    @Override
    public Collection<Hotel> convert(RainbowResponse response) throws ParsingException {
        final Function<ResponseBodyACL, RainbowResponseBodyACL> castToRainbowType = responseBody -> (RainbowResponseBodyACL) responseBody;
        final Function<RainbowResponseBodyACL, RainbowResponseBodyACL> logInfoIfNoOffers = responseBody -> {
            if (isEmpty(responseBody.getBloczki()))
                log.info("response contains no elements to parse. {}", responseBody);
            return responseBody;
        };
        RainbowResponseBodyACL body = Optional.ofNullable(response.getBody())
                .map(castToRainbowType)
                .map(logInfoIfNoOffers)
                .orElseThrow(() -> new ParsingException("Response has no body to parse."));
        return body.getBloczki().stream()
                .map(bloczek -> buildHotelDto(bloczek, response.getTimestamp()))
                .collect(toList());
    }

    private Hotel buildHotelDto(RainbowResponseBodyACL.Bloczek bloczek, LocalDateTime timestamp) {
        return Hotel.builder()
                .code(HotelCode.of(bloczek.getBlok1().getHotelId().toString()))
                .name(HotelName.of(bloczek.getBlok1().getNazwaHotelu()))
                .standard(HotelStandard.of(bloczek.getBlok1().getGwiazdkiHotelu()))
                .opinionScore(HotelOpinionScore.of(bloczek.getOpinie().getOcenaOgolna()))
                .country(evaluateCountry(bloczek))
                .offers(buildOffers(bloczek, timestamp))
                .build();
    }

    private List<Offer> buildOffers(RainbowResponseBodyACL.Bloczek bloczek, LocalDateTime timestamp) {
        return bloczek.getCeny().stream()
                .distinct()
                .map(oferta -> buildOfferDto(oferta, bloczek, timestamp))
                .collect(toList());
    }

    private Offer buildOfferDto(RainbowResponseBodyACL.Bloczek.Oferta oferta, RainbowResponseBodyACL.Bloczek bloczek, LocalDateTime timestamp) {
        String departureTimeRaw = bloczek.getDataWKodzieProduktu();
        DateAndTime departureTime = DateAndTime.of(LocalDateTime.parse(departureTimeRaw, RAINBOW_TOURS.getDateTimeFormatter()));
        City departureCity = City.getByPolishName(oferta.getMiastoWyjazduUrl());
        return Offer.builder()
                .travelAgency(RAINBOW_TOURS)
                .code(OfferCode.of(oferta.getPakietId()))
                .duration(Duration.builder().days(oferta.getLiczbaDni()).build())
                .url(Url.of("http://r.pl" + bloczek.getOfertaUrl()))
                .departure(Departure.of(departureTime, departureCity))
                .board(Board.of(evaluateBoardType(bloczek)))
                .price(Price.of(oferta.getCenaAktualna()))
                .responseTime(ResponseTime.of(timestamp))
                .build();
    }

    private BoardType evaluateBoardType(RainbowResponseBodyACL.Bloczek bloczek) {
        return bloczek.getWyzywienia().stream()
                .filter(RainbowResponseBodyACL.Bloczek.Wyzywienie::isCzyPodstawowe)
                .findFirst()
                .map(RainbowResponseBodyACL.Bloczek.Wyzywienie::getNazwaUrl)
                .map(this::translateBoardType)
                .orElseThrow(UnrecognizedBoardTypeException::new);
    }

    private BoardType translateBoardType(String boardTypeName) {
        Map<String, BoardType> boardTypeTranslator = rainbowTranslator.getBoardTypeTranslator();
        if (boardTypeTranslator.containsKey(boardTypeName.toLowerCase())) {
            return boardTypeTranslator.get(boardTypeName);
        }
        throw new TranslationException("Board type: '" + boardTypeName + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }

    private Country evaluateCountry(RainbowResponseBodyACL.Bloczek bloczek) {
        final Predicate<RainbowResponseBodyACL.Bloczek.Blok1.Lokalizacja> keepCountry = lokalizacja -> negate(lokalizacja.isCzyRegion());
        return bloczek.getBlok1().getLokalizacja().stream()
                .filter(keepCountry)
                .findFirst()
                .map(this::translateCountry)
                .orElseThrow(UnrecognizedCountryException::new);
    }

    private Country translateCountry(RainbowResponseBodyACL.Bloczek.Blok1.Lokalizacja location) {
        String aclCountryComparable = location.getNazwaLokalizacji().toLowerCase();
        Map<String, Country> destinationTranslator = rainbowTranslator.getCountryTranslator();
        if (destinationTranslator.containsKey(aclCountryComparable)) {
            return destinationTranslator.get(aclCountryComparable);
        }
        throw new TranslationException("Country: '" + aclCountryComparable + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }

}
