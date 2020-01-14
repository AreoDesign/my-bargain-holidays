package com.areo.design.holidays.entity;

import com.areo.design.holidays.dictionary.Country;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hotel", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "country"})})
@NamedEntityGraph(name = "graph.hotel.offers.details",
        attributeNodes = @NamedAttributeNode(value = "offers", subgraph = "graph.offers.details"),
        subgraphs = @NamedSubgraph(name = "graph.offers.details", attributeNodes = @NamedAttributeNode("offerDetails"))
)
@Data
@EqualsAndHashCode(exclude = "offers")
@ToString(exclude = {"offers"})
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(precision = 2, scale = 1, nullable = false)
    private Double standard;

    @Column(precision = 2, scale = 1)
    private Double opinion;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<Offer> offers = Sets.newLinkedHashSet();

    @Builder
    public Hotel(String code, String name, Double standard, Double opinion, Country country) {
        this.code = code;
        this.name = name;
        this.standard = standard;
        this.opinion = opinion;
        this.country = country;
    }

    public void setOffers(Set<Offer> offers) {
        offers.forEach(this::addOffer);
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
        offer.setHotel(this);
    }

}
