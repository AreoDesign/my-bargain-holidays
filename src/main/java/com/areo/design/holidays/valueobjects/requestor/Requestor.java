package com.areo.design.holidays.valueobjects.requestor;

import com.areo.design.holidays.valueobjects.atomic.Email;
import com.areo.design.holidays.valueobjects.atomic.Password;
import com.areo.design.holidays.valueobjects.atomic.RequestorState;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Requestor {
    Email login;
    Password password;
    ImmutableSet<SearchCriterion> searchCriteria;
    RequestorState state;
}
