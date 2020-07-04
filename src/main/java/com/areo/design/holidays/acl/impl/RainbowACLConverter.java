package com.areo.design.holidays.acl.impl;

import com.areo.design.holidays.acl.ACLConverter;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.HotelDto;
import com.areo.design.holidays.dto.OfferDetailDto;
import com.areo.design.holidays.dto.OfferDto;
import com.areo.design.holidays.service.translator.impl.RainbowTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.negate;

@Component
@RequiredArgsConstructor
public class RainbowACLConverter implements ACLConverter<RainbowResponseACL> {

    private final RainbowTranslator rainbowTranslator;

    @Override
    public HotelDto convert(RainbowResponseACL rainbowResponseACL) {
        return HotelDto.builder()
                .code(rainbowResponseACL.getBlok1().getHotelId().toString())
                .name(rainbowResponseACL.getBlok1().getNazwaHotelu())
                .standard(rainbowResponseACL.getBlok1().getGwiazdkiHotelu())
                .opinion(rainbowResponseACL.getOpinie().getOcenaOgolna())
                .country(evaluateCountry(rainbowResponseACL))
                .offers(buildOffers(rainbowResponseACL))
                .build();
    }

    private Set<OfferDto> buildOffers(RainbowResponseACL rainbowResponseACL) {
        return rainbowResponseACL.getOferty().stream()
                .map(oferta -> buildOfferDto(oferta, rainbowResponseACL))
                .collect(toSet());
    }

    private OfferDto buildOfferDto(RainbowResponseACL.Oferta oferta, RainbowResponseACL rainbowResponseACL) {
        String departureTimeRaw = rainbowResponseACL.getDataWKodzieProduktu();
        DateTimeFormatter rainbowformatter = DateTimeFormatter.ofPattern(RAINBOW_TOURS.getDateTimeFormat());
        return OfferDto.builder()
                .code(oferta.getPakietId())
                .duration(oferta.getLiczbaDni())
                .url(rainbowResponseACL.getOfertaUrl())
                .departureTime(LocalDateTime.parse(departureTimeRaw, rainbowformatter))
                .boardType(evaluateBoardType(rainbowResponseACL))
                .offerDetails(buildOfferDetails(oferta))
                .build();
    }

    private Set<OfferDetailDto> buildOfferDetails(RainbowResponseACL.Oferta oferta) {
        return Set.of(OfferDetailDto.builder()
                .price(oferta.getCenaAktualna())
                .build());
    }

    private BoardType evaluateBoardType(RainbowResponseACL rainbowResponseACL) {
        return rainbowResponseACL.getWyzywienia().stream()
                .filter(RainbowResponseACL.Wyzywienie::isCzyPodstawowe)
                .findFirst()
                .map(RainbowResponseACL.Wyzywienie::getNazwaUrl)
                .map(this::getBoardType)
                .get();
    }

    private BoardType getBoardType(String boardTypeName) {
        Map<String, BoardType> boardTypeTranslator = rainbowTranslator.getBoardTypeTranslator();
        if (boardTypeTranslator.containsKey(boardTypeName.toLowerCase())) {
            return boardTypeTranslator.get(boardTypeName);
        }
        throw new RuntimeException("Board type: '" + boardTypeName + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }

    private Country evaluateCountry(RainbowResponseACL hotelRainbowACL) {
        return hotelRainbowACL.getBlok1().getLokalizacja().stream()
                .filter(this::keepCountry)
                .findFirst()
                .map(this::decodeCountryACL)
                .get();
    }

    private boolean keepCountry(RainbowResponseACL.Blok1.Lokalizacja lokalizacja) {
        return negate(lokalizacja.isCzyRegion());
    }

    private Country decodeCountryACL(RainbowResponseACL.Blok1.Lokalizacja location) {
        String aclCountryComparable = location.getNazwaLokalizacji().toLowerCase();
        Map<String, Country> destinationTranslator = rainbowTranslator.getDestinationTranslator();
        if (destinationTranslator.containsKey(aclCountryComparable)) {
            return destinationTranslator.get(aclCountryComparable);
        }
        throw new RuntimeException("Country: '" + aclCountryComparable + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }
}
