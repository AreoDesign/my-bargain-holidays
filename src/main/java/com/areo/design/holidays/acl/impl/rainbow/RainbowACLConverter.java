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
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.areo.design.holidays.dictionary.TravelAgency.RAINBOW_TOURS;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.BooleanUtils.negate;

@RequiredArgsConstructor
public class RainbowACLConverter implements ACLConverter<RainbowResponseBodyACL> {

    private final RainbowTranslator rainbowTranslator;

    @Override
    public Collection<HotelDto> convert(RainbowResponseBodyACL responseACL) {
        return responseACL.getBloczki().stream()
                .map(this::buildHotelDto)
                .collect(Collectors.toList());
    }

    private HotelDto buildHotelDto(RainbowResponseBodyACL.Bloczek bloczek) {
        return HotelDto.builder()
                .code(bloczek.getBlok1().getHotelId().toString())
                .name(bloczek.getBlok1().getNazwaHotelu())
                .standard(bloczek.getBlok1().getGwiazdkiHotelu())
                .opinion(bloczek.getOpinie().getOcenaOgolna())
                .country(evaluateCountry(bloczek))
                .offers(buildOffers(bloczek))
                .build();
    }

    private Set<OfferDto> buildOffers(RainbowResponseBodyACL.Bloczek bloczek) {
        return bloczek.getCeny().stream()
                .map(oferta -> buildOfferDto(oferta, bloczek))
                .collect(toSet());
    }

    private OfferDto buildOfferDto(RainbowResponseBodyACL.Bloczek.Oferta oferta, RainbowResponseBodyACL.Bloczek bloczek) {
        String departureTimeRaw = bloczek.getDataWKodzieProduktu();
        return OfferDto.builder()
                .code(oferta.getPakietId())
                .duration(oferta.getLiczbaDni())
                .url(bloczek.getOfertaUrl())
                .departureTime(LocalDateTime.parse(departureTimeRaw, RAINBOW_TOURS.getDateTimeFormatter()))
                .boardType(evaluateBoardType(bloczek))
                .details(buildDetailsDto(oferta))
                .build();
    }

    private Set<DetailDto> buildDetailsDto(RainbowResponseBodyACL.Bloczek.Oferta oferta) {
        return Set.of(DetailDto.builder()
                .price(oferta.getCenaAktualna())
                .requestTime(DetailDto.RequestTime.blank())
                .build());
    }

    private BoardType evaluateBoardType(RainbowResponseBodyACL.Bloczek bloczek) {
        return bloczek.getWyzywienia().stream()
                .filter(RainbowResponseBodyACL.Bloczek.Wyzywienie::isCzyPodstawowe)
                .findFirst()
                .map(RainbowResponseBodyACL.Bloczek.Wyzywienie::getNazwaUrl)
                .map(this::translateBoardType)
                .get();
    }

    private BoardType translateBoardType(String boardTypeName) {
        Map<String, BoardType> boardTypeTranslator = rainbowTranslator.getBoardTypeTranslator();
        if (boardTypeTranslator.containsKey(boardTypeName.toLowerCase())) {
            return boardTypeTranslator.get(boardTypeName);
        }
        throw new TranslationException("Board type: '" + boardTypeName + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }

    private Country evaluateCountry(RainbowResponseBodyACL.Bloczek bloczek) {
        return bloczek.getBlok1().getLokalizacja().stream()
                .filter(keepCountry())
                .findFirst()
                .map(this::translateCountry)
                .get();
    }

    private Predicate<RainbowResponseBodyACL.Bloczek.Blok1.Lokalizacja> keepCountry() {
        return lokalizacja -> negate(lokalizacja.isCzyRegion());
    }

    private Country translateCountry(RainbowResponseBodyACL.Bloczek.Blok1.Lokalizacja location) {
        String aclCountryComparable = location.getNazwaLokalizacji().toLowerCase();
        Map<String, Country> destinationTranslator = rainbowTranslator.getDestinationTranslator();
        if (destinationTranslator.containsKey(aclCountryComparable)) {
            return destinationTranslator.get(aclCountryComparable);
        }
        throw new TranslationException("Country: '" + aclCountryComparable + "' was not found in translator: " + rainbowTranslator.getClass().getSimpleName());
    }
}
