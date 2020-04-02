package com.areo.design.holidays.entity;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "requestor")
@NamedEntityGraph(name = "graph.requestor.search_criteria.alert_criteria",
        attributeNodes = {@NamedAttributeNode(value = "searchCriteria"), @NamedAttributeNode("alertCriteria")}
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"searchCriteria", "alertCriteria"})
@ToString(exclude = {"searchCriteria", "alertCriteria"})
public class RequestorEntity implements Serializable {

    private static final long serialVersionUID = -3908524041570654451L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NaturalId
    @Column(unique = true, nullable = false)
    @NotBlank(message = "login cannot be null nor empty.")
    @Email(message = "login must be valid email address.")
    private String login;

    @NotBlank(message = "login cannot be null nor empty.")
    @Size(min = 5, max = 30, message = "password length must be within 5 to 30 signs")
    @Column(length = 30, nullable = false)
    private String password;

    @OneToMany(mappedBy = "requestor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<SearchCriterionEntity> searchCriteria = Sets.newLinkedHashSet();

    @OneToMany(mappedBy = "requestor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AlertCriterionEntity> alertCriteria = Sets.newLinkedHashSet();

    @Column(name = "creation_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    private boolean active;

    public void setSearchCriteria(Set<SearchCriterionEntity> searchCriteria) {
        searchCriteria.forEach(this::addSearchCriterion);
    }

    public void addSearchCriterion(SearchCriterionEntity searchCriterion) {
        this.searchCriteria.add(searchCriterion);
        searchCriterion.setRequestor(this);
    }

    public void setAlertCriteria(Set<AlertCriterionEntity> alertCriteria) {
        alertCriteria.forEach(this::addAlertCriterion);
    }

    public void addAlertCriterion(AlertCriterionEntity alertCriterion) {
        this.alertCriteria.add(alertCriterion);
        alertCriterion.setRequestor(this);
    }

}
