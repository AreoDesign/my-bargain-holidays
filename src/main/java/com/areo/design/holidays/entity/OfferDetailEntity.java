package com.areo.design.holidays.entity;

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
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_detail", uniqueConstraints = {@UniqueConstraint(columnNames = {"offer_id", "request_time"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "offer")
@ToString(exclude = "offer")
public class OfferDetailEntity implements Serializable {

    private static final long serialVersionUID = 4259734614747203972L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer;

    @Column(name = "request_time", nullable = false)
    private LocalDateTime requestTime;

    @Column(name = "standard_price_per_person", nullable = false)
    private Integer standardPricePerPerson;

    @Column(name = "discount_price_per_person")
    private Integer discountPricePerPerson;

}
