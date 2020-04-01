package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.OfferDetailDto;
import com.areo.design.holidays.entity.OfferDetailEntity;
import org.springframework.stereotype.Component;

@Component
public class OfferDetailConverter implements EntityDtoConverter<OfferDetailEntity, OfferDetailDto> {

    @Override
    public OfferDetailEntity convertToEntity(OfferDetailDto dto) {
        return OfferDetailEntity.builder()
                .id(dto.getId())
                .requestTime(dto.getRequestTime())
                .standardPricePerPerson(dto.getStandardPricePerPerson())
                .discountPricePerPerson(dto.getDiscountPricePerPerson())
                .build();
    }

    @Override
    public OfferDetailDto convertToDto(OfferDetailEntity entity) {
        return OfferDetailDto.builder()
                .id(entity.getId())
                .requestTime(entity.getRequestTime())
                .standardPricePerPerson(entity.getStandardPricePerPerson())
                .discountPricePerPerson(entity.getDiscountPricePerPerson())
                .build();
    }
}
