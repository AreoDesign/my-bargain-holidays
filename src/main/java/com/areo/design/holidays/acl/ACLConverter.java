package com.areo.design.holidays.acl;

import com.areo.design.holidays.dto.offer.HotelDto;

import java.util.Collection;

public interface ACLConverter<T extends ResponseBodyACL> {
    Collection<HotelDto> convert(T responseACL);
}
