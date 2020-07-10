package com.areo.design.holidays.acl;

import com.areo.design.holidays.component.translator.Translable;
import com.areo.design.holidays.dto.SearchCriterionDto;

public interface PayloadPreparatorACL<V extends PayloadTemplateACL> {
    V prepare(SearchCriterionDto searchCriterionDto);

    Translable getTranslator();
}
