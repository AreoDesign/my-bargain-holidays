package com.areo.design.holidays.converter.impl.offer;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.entity.offer.DetailEntity;
import com.areo.design.holidays.valueobjects.offer.Detail;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class OfferDetailConverter implements EntityDtoConverter<DetailEntity, Detail> {

    @Override
    public DetailEntity convertToEntity(Detail dto) {
        return isNull(dto) ? null :
                DetailEntity.builder()
                        .id(dto.getId())
                        .requestTime(dto.getRequestTime().toLocalDateTime())
                        .standardPricePerPerson(dto.getPrice())
                        .build();
    }

    @Override
    public Detail convertToDto(DetailEntity entity) {
        return isNull(entity) ? null :
                Detail.builder()
                        .id(entity.getId())
                        .requestTime(Detail.RequestTime.builder()
                                .responseHeaderTime(entity.getRequestTime())
                                .build())
                        .price(entity.getStandardPricePerPerson())
                        .build();
    }
}
