package com.areo.design.holidays.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class RequestorDto implements Serializable {
    private static final long serialVersionUID = 8545031026254129106L;
    private UUID id;
    private String login;
    private String password;
    private Set<SearchCriterionDto> searchCriteria;
    private boolean isActive;
}
