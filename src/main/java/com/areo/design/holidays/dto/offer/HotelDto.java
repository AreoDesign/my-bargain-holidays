package com.areo.design.holidays.dto.offer;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.EntityConvertible;
import com.areo.design.holidays.entity.offer.HotelEntity;
import com.areo.design.holidays.entity.offer.OfferEntity;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toSet;

@Value
@Builder
public class HotelDto implements Serializable, EntityConvertible<HotelEntity> {
    private static final long serialVersionUID = 7386180447845519637L;
    private UUID id;
    private String code;
    private String name;
    private Double standard;
    private Double opinion;
    private Country country;
    private Set<OfferDto> offers;

    //fixme: this shall replace converter
    @Override
    public HotelEntity toEntity() {
        return HotelEntity.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .standard(this.standard)
                .opinion(this.opinion)
                .country(this.country)
                .offers(getOfferEntities())
                .build();
    }

    private Set<OfferEntity> getOfferEntities() {
        return this.offers.stream()
                .map(OfferDto::toEntity)
                .collect(toSet());
    }
}
