package com.areo.design.holidays.valueobjects.requestor;

import com.areo.design.holidays.entity.requestor.AlertCriterionEntity;
import com.areo.design.holidays.entity.requestor.RequestorEntity;
import com.areo.design.holidays.entity.requestor.SearchCriterionEntity;
import com.areo.design.holidays.valueobjects.EntityConvertible;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@Builder
public class Requestor implements Serializable, EntityConvertible<RequestorEntity> {
    private static final long serialVersionUID = 8545031026254129106L;
    private UUID id;
    private String login;
    private String password;
    private Set<SearchCriterion> searchCriteria;
    private Set<AlertCriterion> alertCriteria;
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
                .map(SearchCriterion::toEntity)
                .collect(Collectors.toSet());
    }

    private Set<AlertCriterionEntity> convertAlertCriteria() {
        return this.alertCriteria.stream()
                .map(AlertCriterion::toEntity)
                .collect(Collectors.toSet());
    }
}
