package com.areo.design.holidays.converter.impl.requestor;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dictionary.Country;
import com.areo.design.holidays.entity.requestor.AlertCriterionEntity;
import com.areo.design.holidays.entity.requestor.RequestorEntity;
import com.areo.design.holidays.valueobjects.requestor.AlertCriterion;
import org.springframework.stereotype.Component;

import static com.areo.design.holidays.converter.EntityDtoConverter.collectionOfEnumsAsString;
import static com.areo.design.holidays.converter.EntityDtoConverter.stringAsCollectionOfEnum;
import static java.util.Objects.isNull;

@Component
public class AlertCriterionConverter implements EntityDtoConverter<AlertCriterionEntity, AlertCriterion> {

    @Override
    public AlertCriterionEntity convertToEntity(AlertCriterion dto) {
        return isNull(dto) ? null :
                AlertCriterionEntity.builder()
                        .id(dto.getId())
                        .requestor(RequestorEntity.builder().id(dto.getRequestorId()).build())
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
    public AlertCriterion convertToDto(AlertCriterionEntity entity) {
        return isNull(entity) ? null :
                AlertCriterion.builder()
                        .id(entity.getId())
                        .requestorId(entity.getRequestor().getId())
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
