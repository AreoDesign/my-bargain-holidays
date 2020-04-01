package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.OfferDto;
import com.areo.design.holidays.entity.OfferEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class OfferConverter implements EntityDtoConverter<OfferEntity, OfferDto> {

    private OfferDetailConverter offerDetailConverter;

    @Override
    public OfferEntity convertToEntity(OfferDto dto) {
        return OfferEntity.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .url(dto.getUrl())
                .departureTime(dto.getDepartureTime())
                .boardType(dto.getBoardType())
                .duration(dto.getDuration())
                .offerDetails(Set.copyOf(offerDetailConverter.convertToEntities(dto.getOfferDetails())))
                .build();
    }

    @Override
    public OfferDto convertToDto(OfferEntity entity) {
        return OfferDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .url(entity.getUrl())
                .departureTime(entity.getDepartureTime())
                .boardType(entity.getBoardType())
                .duration(entity.getDuration())
                .offerDetails(Set.copyOf(offerDetailConverter.convertToDtos(entity.getOfferDetails())))
                .build();
    }
}
