package com.areo.design.holidays.dto;

import com.areo.design.holidays.dictionary.Country;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class AlertCriterionDto implements Serializable {
    private static final long serialVersionUID = 8427247980425486640L;
    private Integer id;
    private UUID requestorId;
    private String email;
    private LocalDate holidayStart;
    private LocalDate holidayEnd;
    private Set<Country> countries;
    private Integer priceMax;
    private Double minHotelStandard;
    private boolean active;
}
