package com.areo.design.holidays.valueobjects.requestor;

import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.requestor.AlertCriterionEntity;
import com.areo.design.holidays.entity.requestor.RequestorEntity;
import com.areo.design.holidays.valueobjects.EntityConvertible;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfEnumsAsString;

@Value
@Builder
public class AlertCriterion implements Serializable, EntityConvertible<AlertCriterionEntity> {
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

    //fixme: this shall replace converter
    @Override
    public AlertCriterionEntity toEntity() {
        return AlertCriterionEntity.builder()
                .id(this.id)
                .requestor(RequestorEntity.builder().id(this.requestorId).build())
                .email(this.email)
                .holidayStart(this.holidayStart)
                .holidayEnd(this.holidayEnd)
                .countries(collectionOfEnumsAsString(this.countries))
                .priceMax(this.priceMax)
                .minHotelStandard(this.minHotelStandard)
                .active(this.active)
                .build();
    }
}
