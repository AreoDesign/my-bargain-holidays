package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.offer.DetailDto;
import com.areo.design.holidays.entity.DetailEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OfferDetailConverter implements EntityDtoConverter<DetailEntity, DetailDto> {

    @Override
    public DetailEntity convertToEntity(DetailDto dto) {
        return isNull(dto) ? null :
                DetailEntity.builder()
                        .id(dto.getId())
                        .requestTime(dto.getRequestTime().toLocalDateTime())
                        .standardPricePerPerson(dto.getPrice())
                        .build();
    }

    @Override
    public DetailDto convertToDto(DetailEntity entity) {
        return isNull(entity) ? null :
                DetailDto.builder()
                        .id(entity.getId())
                        .requestTime(DetailDto.RequestTime.builder()
                                .responseHeaderTime(entity.getRequestTime())
                                .build())
                        .price(entity.getStandardPricePerPerson())
                        .build();
    }
}
