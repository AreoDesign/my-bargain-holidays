package com.areo.design.holidays.repository;

import com.areo.design.holidays.entity.Requestor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestorRepository extends JpaRepository<Requestor, UUID> {
}
