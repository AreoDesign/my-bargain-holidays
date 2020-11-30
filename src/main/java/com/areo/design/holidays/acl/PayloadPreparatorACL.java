package com.areo.design.holidays.acl;

import com.areo.design.holidays.component.translator.Translable;
import com.areo.design.holidays.valueobjects.requestor.SearchCriterion;

public interface PayloadPreparatorACL<T extends PayloadTemplateACL> {
    T prepare(SearchCriterion searchCriterion);

    Translable getTranslator();
}
