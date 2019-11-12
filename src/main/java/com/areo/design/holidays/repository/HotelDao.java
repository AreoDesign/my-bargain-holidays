package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HotelDao extends JpaRepository<Hotel, UUID> {
}
