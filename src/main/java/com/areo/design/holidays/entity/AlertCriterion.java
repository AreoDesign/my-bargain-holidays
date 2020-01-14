package com.areo.design.holidays.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "alert_criterion")
@Data
public class AlertCriterion {

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private SearchCriterion searchCriterion;

    @Email
    @NonNull
    @NotBlank(message = "Alert criterion must have valid email defined to send notifications to")
    private String email;

    private LocalDate holidayStart;

    private LocalDate holidayEnd;

    private String countries;           //conversion to Set<Country> done by converter

    private Integer priceMax;

    private Double minHotelStandard;

    private boolean isActive;

    @Builder
    public AlertCriterion(String email, LocalDate holidayStart, LocalDate holidayEnd,
                          String countries, Integer priceMax, Double minHotelStandard) {
        this.email = email;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.countries = countries;
        this.priceMax = priceMax;
        this.minHotelStandard = minHotelStandard;
        this.isActive = true;
    }
}
