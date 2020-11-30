package com.areo.design.holidays.converter.impl.requestor;

import com.areo.design.holidays.converter.EntityDtoConverter;
import com.areo.design.holidays.entity.requestor.RequestorEntity;
import com.areo.design.holidays.valueobjects.requestor.Requestor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class RequestorConverter implements EntityDtoConverter<RequestorEntity, Requestor> {

    private final SearchCriterionConverter searchCriterionConverter;

    //FIXME: consider usage - Maps.asConverter(BiMap)

    @Override
    public RequestorEntity convertToEntity(Requestor dto) {
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
    public Requestor convertToDto(RequestorEntity entity) {
        return isNull(entity) ? null :
                Requestor.builder()
                        .id(entity.getId())
                        .login(entity.getLogin())
                        .password(entity.getPassword())
                        .searchCriteria(Set.copyOf(searchCriterionConverter.convertToDtos(entity.getSearchCriteria())))
                        .active(entity.isActive())
                        .build();
    }

}
