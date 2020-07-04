package com.areo.design.holidays.acl;

import com.areo.design.holidays.dto.HotelDto;

public interface ACLConverter<V extends ResponseACL> {
    HotelDto convert(V hotelACL);
}
