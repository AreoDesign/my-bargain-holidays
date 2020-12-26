package com.areo.design.holidays.acl;

import com.areo.design.holidays.component.response.Response;
import com.areo.design.holidays.valueobjects.offer.Hotel;

import java.util.Collection;

public interface ACLConverter<T extends Response> {
    Collection<Hotel> convert(T response);
}
