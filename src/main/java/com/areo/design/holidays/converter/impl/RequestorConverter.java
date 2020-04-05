package com.areo.design.holidays.converter.impl;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.dto.RequestorDto;
import com.areo.design.holidays.entity.RequestorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class RequestorConverter implements EntityDtoConverter<RequestorEntity, RequestorDto> {

    private final SearchCriterionConverter searchCriterionConverter;

    @Override
    public RequestorEntity convertToEntity(RequestorDto dto) {
        return isNull(dto) ? null :
                RequestorEntity.builder()
                        .id(dto.getId())
                        .login(dto.getLogin())
                        .password(dto.getPassword())
                        .searchCriteria(Set.copyOf(searchCriterionConverter.convertToEntities(dto.getSearchCriteria())))
                        .active(dto.isActive())
                        .build();
    }

    @Override
    public RequestorDto convertToDto(RequestorEntity entity) {
        return isNull(entity) ? null :
                RequestorDto.builder()
                        .id(entity.getId())
                        .login(entity.getLogin())
                        .password(entity.getPassword())
                        .searchCriteria(Set.copyOf(searchCriterionConverter.convertToDtos(entity.getSearchCriteria())))
                        .active(entity.isActive())
                        .build();
    }

}
