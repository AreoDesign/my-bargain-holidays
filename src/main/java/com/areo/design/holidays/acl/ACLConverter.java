package com.areo.design.holidays.acl;

import com.areo.design.holidays.valueobjects.offer.Hotel;

import java.util.Collection;

public interface ACLConverter<T extends ResponseBodyACL> {
    Collection<Hotel> convert(T responseACL);
}
