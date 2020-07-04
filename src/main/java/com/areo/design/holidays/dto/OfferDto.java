package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.BoardType;
import com.areo.design.holidays.entity.OfferDetailEntity;
import com.areo.design.holidays.entity.OfferEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class OfferDto implements Serializable {
    private static final long serialVersionUID = 6982747557763042694L;
    private UUID id;
    private UUID hotelId;
    private String code;
    private String url;
    private LocalDateTime departureTime;
    private BoardType boardType;
    private Integer duration;
    private Set<OfferDetailDto> offerDetails;

    //fixme: this shall replace converter
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

    private Set<OfferDetailEntity> getOfferDetailEntities() {
        return this.offerDetails.stream()
                .map(OfferDetailDto::toEntity)
                .collect(Collectors.toSet());
    }
}
