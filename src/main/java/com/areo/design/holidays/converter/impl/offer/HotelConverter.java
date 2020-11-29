package com.areo.design.holidays.converter.impl.offer;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.offer.HotelDto;
import com.areo.design.holidays.entity.offer.HotelEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class HotelConverter implements EntityDtoConverter<HotelEntity, HotelDto> {

    private final OfferConverter offerConverter;

    @Override
    public HotelEntity convertToEntity(HotelDto dto) {
        return isNull(dto) ? null :
                HotelEntity.builder()
                        .id(dto.getId())
                        .code(dto.getCode())
                        .name(dto.getName())
                        .standard(dto.getStandard())
                        .opinion(dto.getOpinion())
                        .country(dto.getCountry())
                        .offers(Set.copyOf(offerConverter.convertToEntities(dto.getOffers())))
                        .build();
    }

    @Override
    public HotelDto convertToDto(HotelEntity entity) {
        return isNull(entity) ? null :
                HotelDto.builder()
                        .id(entity.getId())
                        .code(entity.getCode())
                        .name(entity.getName())
                        .standard(entity.getStandard())
                        .opinion(entity.getOpinion())
                        .country(entity.getCountry())
                        .offers(Set.copyOf(offerConverter.convertToDtos(entity.getOffers())))
                        .build();
    }

}
