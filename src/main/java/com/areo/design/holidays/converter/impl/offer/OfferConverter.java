package com.areo.design.holidays.converter.impl.offer;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.entity.offer.OfferEntity;
import com.areo.design.holidays.valueobjects.offer.Offer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class OfferConverter implements EntityDtoConverter<OfferEntity, Offer> {

    private final OfferDetailConverter offerDetailConverter;

    @Override
    public OfferEntity convertToEntity(Offer dto) {
        return isNull(dto) ? null :
                OfferEntity.builder()
                        .id(dto.getId())
                        .code(dto.getCode())
                        .url(dto.getUrl())
                        .departureTime(dto.getDepartureTime())
                        .boardType(dto.getBoardType())
                        .duration(dto.getDuration())
                        .offerDetails(Set.copyOf(offerDetailConverter.convertToEntities(dto.getDetails())))
                        .build();
    }

    @Override
    public Offer convertToDto(OfferEntity entity) {
        return isNull(entity) ? null :
                Offer.builder()
                        .id(entity.getId())
                        .code(entity.getCode())
                        .url(entity.getUrl())
                        .departureTime(entity.getDepartureTime())
                        .boardType(entity.getBoardType())
                        .duration(entity.getDuration())
                        .details(Set.copyOf(offerDetailConverter.convertToDtos(entity.getOfferDetails())))
                        .build();
    }
}
