package com.areo.design.holidays.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "alert_criterion")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "searchCriterion")
@ToString(exclude = {"searchCriterion"})
public class AlertCriterionEntity implements Serializable {

    private static final long serialVersionUID = -3484976626142257431L;

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private SearchCriterionEntity searchCriterion;

    @Email(message = "Alert criterion must have valid email defined to send notifications to")
    @NotBlank(message = "email field cannot be blank")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate holidayStart;

    @Column(nullable = false)
    private LocalDate holidayEnd;

    @Column(nullable = false)
    private String countries;           //conversion to Set<Country> done by converter

    @Column(nullable = false)
    private Integer priceMax;

    @Column(nullable = false)
    private Double minHotelStandard;

    private boolean isActive = true;

    @Builder
    public AlertCriterionEntity(String email, LocalDate holidayStart, LocalDate holidayEnd,
                                String countries, Integer priceMax, Double minHotelStandard) {
        this.email = email;
        this.holidayStart = holidayStart;
        this.holidayEnd = holidayEnd;
        this.countries = countries;
        this.priceMax = priceMax;
        this.minHotelStandard = minHotelStandard;
    }
}
