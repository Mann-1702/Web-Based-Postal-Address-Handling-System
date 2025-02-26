package edu.seattleu.addressmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
@Schema(name = "City", description = "Represents a city within a state/province")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the city", example = "100")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Name of the city", example = "Seattle")
    @JsonProperty("name")
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    @Schema(description = "Associated state or province")
    @JsonIgnore // Exclude the entire country object from JSON
    @JsonIgnoreProperties({"cities"})
    private StateProvince state;

    @JsonProperty("stateId")
    public Long getStateId() {
        return state != null ? state.getId() : null;
    }

    @JsonProperty("countryId")
    public Long getCountryId() {
        return (state != null && state.getCountry() != null) ? state.getCountry().getId() : null;
    }

    public City() {}

    public City(String name, StateProvince state) {
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateProvince getState() {
        return state;
    }

    public void setState(StateProvince state) {
        this.state = state;
    }


}
