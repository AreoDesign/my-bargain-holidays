package com.areo.design.holidays.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "search_criterion")
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"requestor", "alertCriterion"})
@ToString(exclude = {"requestor", "alertCriterion"})
public class SearchCriterionEntity implements Serializable {

    private static final long serialVersionUID = -2288918626193593609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requestor_id", referencedColumnName = "id", nullable = false)
    private RequestorEntity requestor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "searchCriterion")
    private AlertCriterionEntity alertCriterion;

    @Column(name = "children_birth_dates")
    private String childrenBirthDates;  //conversion to Set<LocalDate> done by converter

    @NonNull
    @NotBlank(message = "must define at least one adult person birth date!")
    @Column(name = "adults_birth_dates", nullable = false)
    private String adultsBirthDates;    //conversion to Set<LocalDate> done by converter

    @Column(name = "departure_date_from", nullable = false)
    @FutureOrPresent
    private LocalDate departureDateFrom;

    @Column(name = "departure_date_to", nullable = false)
    @Future
    private LocalDate departureDateTo;

    @Min(value = 3, message = "minimum stay length must not be less than 3 days!")
    @Max(value = 30, message = "minimum stay length must not be greater than 30 days!")
    @Column(name = "stay_length", nullable = false)
    private Integer stayLength;

    @Column(name = "departure_cities", nullable = false)
    private String departureCities;     //conversion to Set<City> done by converter

    @Column(name = "board_types", nullable = false)
    private String boardTypes;          //conversion to Set<BoardType> done by converter

    @Column(name = "countries", nullable = false)
    private String countries;           //conversion to Set<Country> done by converter

    @Min(value = 1, message = "Minimum hotel standard is one star! *")
    @Max(value = 5, message = "Maximum hotel standard is five stars! *****")
    @Column(name = "min_hotel_std", precision = 2, scale = 1, nullable = false)
    private Double minHotelStandard = 3d;   //hotel standard in 'stars' unit

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private boolean isActive = true;

    public void setAlertCriterion(AlertCriterionEntity alertCriterion) {
        this.alertCriterion = alertCriterion;
        alertCriterion.setSearchCriterion(this);
    }
}
