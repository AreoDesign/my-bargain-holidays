package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.Country;
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

}
