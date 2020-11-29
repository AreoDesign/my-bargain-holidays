package com.areo.design.holidays.entity.requestor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "alert_criterion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "requestor")
@ToString(exclude = {"requestor"})
public class AlertCriterionEntity implements Serializable {

    private static final long serialVersionUID = -3484976626142257431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requestor_id", referencedColumnName = "id", nullable = false)
    private RequestorEntity requestor;

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

    @Builder.Default
    private boolean active = true;

}
