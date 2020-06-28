package com.areo.design.holidays.entity;

import com.areo.design.holidays.dictionary.BoardType;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "offer")
@NamedEntityGraph(name = "graph.offer.details",
        attributeNodes = @NamedAttributeNode(value = "offerDetails")
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"hotel", "offerDetails"})
@ToString(exclude = {"hotel", "offerDetails"})
public class OfferEntity implements Serializable {

    private static final long serialVersionUID = -6523944150626514781L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OfferDetailEntity> offerDetails = Sets.newLinkedHashSet();

    private String code;

    @NaturalId
    @Column(columnDefinition = "text", unique = true, nullable = false)
    private String url;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false)
    private BoardType boardType;

    @Column(nullable = false)
    private Integer duration;

    public void setOfferDetails(Set<OfferDetailEntity> offerDetails) {
        offerDetails.forEach(this::addOfferDetail);
    }

    public void addOfferDetail(OfferDetailEntity offerDetail) {
        this.offerDetails.add(offerDetail);
        offerDetail.setOffer(this);
    }


}