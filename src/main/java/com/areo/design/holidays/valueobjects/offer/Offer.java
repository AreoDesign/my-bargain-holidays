package com.areo.design.holidays.valueobjects.offer;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.entity.offer.DetailEntity;
import com.areo.design.holidays.entity.offer.OfferEntity;
import com.areo.design.holidays.valueobjects.EntityConvertible;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Value
@Builder
public class Offer implements Serializable, EntityConvertible<OfferEntity> {
    private static final long serialVersionUID = 6982747557763042694L;
    private UUID id;
    private UUID hotelId;
    private String code;
    private String url;
    private LocalDateTime departureTime;
    private BoardType boardType;
    private Integer duration;
    private Set<Detail> details;

    //fixme: this shall replace converter
    @Override
    public OfferEntity toEntity() {
        return OfferEntity.builder()
                .id(this.id)
                .code(this.code)
                .url(this.url)
                .departureTime(this.departureTime)
                .boardType(this.boardType)
                .duration(this.duration)
                .offerDetails(getOfferDetailEntities())
                .build();
    }

    private Set<DetailEntity> getOfferDetailEntities() {
        return this.details.stream()
                .map(Detail::toEntity)
                .collect(toSet());
    }
}
