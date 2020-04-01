package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.dto.AlertCriterionDto;
import com.areo.design.holidays.entity.AlertCriterionEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertCriterionConverter implements EntityDtoConverter<AlertCriterionEntity, AlertCriterionDto> {

    @Override
    public AlertCriterionEntity convertToEntity(AlertCriterionDto dto) {
        return AlertCriterionEntity.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .holidayStart(dto.getHolidayStart())
                .holidayEnd(dto.getHolidayEnd())
                .countries(collectionOfEnumsAsString(dto.getCountries()))
                .priceMax(dto.getPriceMax())
                .minHotelStandard(dto.getMinHotelStandard())
                .active(dto.isActive())
                .build();
    }

    @Override
    public AlertCriterionDto convertToDto(AlertCriterionEntity entity) {
        return AlertCriterionDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .holidayStart(entity.getHolidayStart())
                .holidayEnd(entity.getHolidayEnd())
                .countries(stringAsCollectionOfEnum(Country.class, entity.getCountries()))
                .priceMax(entity.getPriceMax())
                .minHotelStandard(entity.getMinHotelStandard())
                .active(entity.isActive())
                .build();
    }

}
