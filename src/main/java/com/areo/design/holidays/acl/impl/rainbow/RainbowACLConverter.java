package com.areo.design.holidays.acl.impl.rainbow;

import com.areo.design.holidays.acl.ACLConverter;
import com.areo.design.holidays.component.translator.impl.RainbowTranslator;
import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.offer.DetailDto;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.dto.offer.OfferDto;
import com.areo.design.holidays.exception.TranslationException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.negate;

@RequiredArgsConstructor
public class RainbowACLConverter implements ACLConverter<RainbowResponseACL> {

    private final RainbowTranslator rainbowTranslator;

    @Override
    public Collection<HotelDto> convert(RainbowResponseACL responseACL) {
        return responseACL.getBloczki().stream()
                .map(this::buildHotelDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private HotelDto buildHotelDto(RainbowResponseACL.Bloczek bloczek) {
        return HotelDto.builder()
                .code(bloczek.getBlok1().getHotelId().toString())
                .name(bloczek.getBlok1().getNazwaHotelu())
                .standard(bloczek.getBlok1().getGwiazdkiHotelu())
                .opinion(bloczek.getOpinie().getOcenaOgolna())
                .country(evaluateCountry(bloczek))
                .offers(buildOffers(bloczek))
                .build();
    }

    private Set<OfferDto> buildOffers(RainbowResponseACL.Bloczek bloczek) {
        return bloczek.getCeny().stream()
                .map(oferta -> buildOfferDto(oferta, bloczek))
                .collect(toSet());
    }

    private OfferDto buildOfferDto(RainbowResponseACL.Bloczek.Oferta oferta, RainbowResponseACL.Bloczek bloczek) {
        String departureTimeRaw = bloczek.getDataWKodzieProduktu();
        DateTimeFormatter rainbowformatter = DateTimeFormatter.ofPattern(RAINBOW_TOURS.getDateTimeFormat());
        return OfferDto.builder()
                .code(oferta.getPakietId())
                .duration(oferta.getLiczbaDni())
                .url(bloczek.getOfertaUrl())
                .departureTime(LocalDateTime.parse(departureTimeRaw, rainbowformatter))
                .boardType(evaluateBoardType(bloczek))
                .details(buildDetailsDto(oferta))
                .build();
    }

    private Set<DetailDto> buildDetailsDto(RainbowResponseACL.Bloczek.Oferta oferta) {
        return Set.of(DetailDto.builder()
                .price(oferta.getCenaAktualna())
                .requestTime(DetailDto.RequestTime.builder().responseHeaderTime(LocalDateTime.now()).build())
                .build());
    }

    private BoardType evaluateBoardType(RainbowResponseACL.Bloczek bloczek) {
        return bloczek.getWyzywienia().stream()
                .filter(RainbowResponseACL.Bloczek.Wyzywienie::isCzyPodstawowe)
                .findFirst()
                .map(RainbowResponseACL.Bloczek.Wyzywienie::getNazwaUrl)
                .map(this::getBoardType)
                .get();
    }

    private BoardType getBoardType(String boardTypeName) {
        Map<String, BoardType> boardTypeTranslator = rainbowTranslator.getBoardTypeTranslator();
        if (boardTypeTranslator.containsKey(boardTypeName.toLowerCase())) {
            return boardTypeTranslator.get(boardTypeName);
        }
        throw new TranslationException("Board type: '" + boardTypeName + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }

    private Country evaluateCountry(RainbowResponseACL.Bloczek bloczek) {
        return bloczek.getBlok1().getLokalizacja().stream()
                .filter(this::keepCountry)
                .findFirst()
                .map(this::decodeCountryACL)
                .get();
    }

    private boolean keepCountry(RainbowResponseACL.Bloczek.Blok1.Lokalizacja lokalizacja) {
        return negate(lokalizacja.isCzyRegion());
    }

    private Country decodeCountryACL(RainbowResponseACL.Bloczek.Blok1.Lokalizacja location) {
        String aclCountryComparable = location.getNazwaLokalizacji().toLowerCase();
        Map<String, Country> destinationTranslator = rainbowTranslator.getDestinationTranslator();
        if (destinationTranslator.containsKey(aclCountryComparable)) {
            return destinationTranslator.get(aclCountryComparable);
        }
        throw new TranslationException("Country: '" + aclCountryComparable + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }
}
