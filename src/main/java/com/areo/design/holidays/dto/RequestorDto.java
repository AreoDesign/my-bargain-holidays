package com.areo.design.holidays.dto;

import com.areo.design.holidays.entity.AlertCriterionEntity;
import com.areo.design.holidays.entity.RequestorEntity;
import com.areo.design.holidays.entity.SearchCriterionEntity;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@Builder
public class RequestorDto implements Serializable, EntityConvertible<RequestorEntity> {
    private static final long serialVersionUID = 8545031026254129106L;
    private UUID id;
    private String login;
    private String password;
    private Set<SearchCriterionDto> searchCriteria;
    private Set<AlertCriterionDto> alertCriteria;
    private boolean active;

    @Override
    public RequestorEntity toEntity() {
        return RequestorEntity.builder()
                .id(this.id)
                .login(this.login)
                .password(this.password)
                .searchCriteria(convertSearchCriteria())
                .alertCriteria(convertAlertCriteria())
                .active(this.active)
                .build();
    }

    private Set<SearchCriterionEntity> convertSearchCriteria() {
        return this.searchCriteria.stream()
                .map(SearchCriterionDto::toEntity)
                .collect(Collectors.toSet());
    }

    private Set<AlertCriterionEntity> convertAlertCriteria() {
        return this.alertCriteria.stream()
                .map(AlertCriterionDto::toEntity)
                .collect(Collectors.toSet());
    }
}
