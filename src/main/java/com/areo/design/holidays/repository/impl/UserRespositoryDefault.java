package com.areo.design.holidays.repository.impl;

import com.areo.design.holidays.repository.UserRepository;
import com.areo.design.holidays.repository.dao.AlertCriterionDAO;
import com.areo.design.holidays.repository.dao.RequestorDAO;
import com.areo.design.holidays.repository.dao.SearchCriterionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRespositoryDefault implements UserRepository {
    private final RequestorDAO requestorDAO;
    private final SearchCriterionDAO searchCriterionDAO;
    private final AlertCriterionDAO alertCriterionDAO;
}
