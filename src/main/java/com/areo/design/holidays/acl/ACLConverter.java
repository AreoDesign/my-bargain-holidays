package com.areo.design.holidays.acl;

import com.areo.design.holidays.dto.offer.HotelDto;

import java.util.Collection;

public interface ACLConverter<T extends ResponseACL> {
    Collection<HotelDto> convert(T responseACL);
}
