package com.areo.design.holidays.entity;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_detail",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"request_time"})}
)
@Data
@EqualsAndHashCode(exclude = "offer")
@ToString(exclude = "offer")
@NoArgsConstructor
public class OfferDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "original_price_per_person")
    private Integer originalPricePerPerson;

    @Column(name = "discount_price_per_person")
    private Integer discountPricePerPerson;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Builder
    public OfferDetail(LocalDateTime requestTime, Integer originalPricePerPerson, Integer discountPricePerPerson) {
        this.requestTime = requestTime;
        this.originalPricePerPerson = originalPricePerPerson;
        this.discountPricePerPerson = discountPricePerPerson;
    }

}
