package com.areo.design.holidays.dto;

import com.areo.design.holidays.entity.OfferDetailEntity;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class OfferDetailDto implements Serializable, EntityConvertible<OfferDetailEntity> {
    private static final long serialVersionUID = -5585468068816091365L;
    private Long id;
    private UUID offerId;
    private LocalDateTime requestTime;
    private Integer price;

    //fixme: this shall replace converter
    @Override
    public OfferDetailEntity toEntity() {
        return OfferDetailEntity.builder()
                .id(this.id)
                .requestTime(this.requestTime)
                .standardPricePerPerson(this.price)
                .build();
    }
}
