package com.areo.design.holidays.entity;

import com.google.common.collect.Sets;
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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "requestor")
@Data
@NamedEntityGraph(name = "graph.requestor.search_criterion.alert_criterion",
        attributeNodes = @NamedAttributeNode(value = "searchCriteria", subgraph = "graph.search_criteria.alert_criterion"),
        subgraphs = @NamedSubgraph(name = "graph.search_criteria.alert_criterion", attributeNodes = @NamedAttributeNode("alertCriterion"))
)
@EqualsAndHashCode(exclude = "searchCriteria")
@ToString(exclude = {"searchCriteria"})
@NoArgsConstructor
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
    private Set<SearchCriterionEntity> searchCriteria = Sets.newLinkedHashSet();

    private boolean isActive;

    @Builder
    public RequestorEntity(String login, String password) {
        this.login = login;
        this.password = password;
        this.isActive = true;
    }

    public void setSearchCriteria(Set<SearchCriterionEntity> searchCriteria) {
        searchCriteria.forEach(this::addSearchCriterion);
    }

    public void addSearchCriterion(SearchCriterionEntity searchCriterion) {
        this.searchCriteria.add(searchCriterion);
        searchCriterion.setRequestor(this);
    }
}
