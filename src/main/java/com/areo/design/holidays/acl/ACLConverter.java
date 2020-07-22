package com.areo.design.holidays.acl;

import com.areo.design.holidays.dto.HotelDto;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ACLConverter<T extends ResponseACL> {
    Collection<HotelDto> convert(ResponseEntity<T> hotelACL);
}
