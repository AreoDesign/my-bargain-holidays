package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.HotelEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class HotelDto implements Serializable {
    private static final long serialVersionUID = 7386180447845519637L;
    private UUID id;
    private String code;
    private String name;
    private Double standard;
    private Double opinion;
    private Country country;
    private Set<OfferDto> offers;

    public HotelEntity toEntity() {
        return HotelEntity.builder()
                .id(this.id)
                .code(this.code)
                .name(this.name)
                .standard(this.standard)
                .opinion(this.opinion)
                .country(this.country)
                .offers(this.offers)
                .build();
    }
}
