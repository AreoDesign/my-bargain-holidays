package com.areo.design.holidays.acl;

import com.areo.design.holidays.component.translator.Translable;
import com.areo.design.holidays.dto.requestor.SearchCriterionDto;

public interface PayloadPreparatorACL<T extends PayloadTemplateACL> {
    T prepare(SearchCriterionDto searchCriterionDto);

    Translable getTranslator();
}
