package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.OfferDetailDto;
import com.areo.design.holidays.entity.OfferDetailEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OfferDetailConverter implements EntityDtoConverter<OfferDetailEntity, OfferDetailDto> {

    @Override
    public OfferDetailEntity convertToEntity(OfferDetailDto dto) {
        return isNull(dto) ? null :
                OfferDetailEntity.builder()
                        .id(dto.getId())
                        .requestTime(dto.getRequestTime())
                        .standardPricePerPerson(dto.getPrice())
                        .build();
    }

    @Override
    public OfferDetailDto convertToDto(OfferDetailEntity entity) {
        return isNull(entity) ? null :
                OfferDetailDto.builder()
                        .id(entity.getId())
                        .requestTime(entity.getRequestTime())
                        .price(entity.getStandardPricePerPerson())
                        .build();
    }
}
