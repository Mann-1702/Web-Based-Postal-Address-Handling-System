package edu.seattleu.addressmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "states")
@Schema(name = "StateProvince", description = "Represents a state or province within a country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StateProvince {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the state/province", example = "10")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Name of the state/province", example = "Washington")
    @JsonProperty("name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @Schema(description = "Associated country")
    @JsonIgnore // Exclude the entire country object from JSON
    @JsonIgnoreProperties({"state", "countries"})
    private Country country;

    @JsonProperty("countryId")
    public Long getCountryId() {
        return country != null ? country.getId() : null;
    }

    public StateProvince() {
        // Default no-args constructor
    }

    public StateProvince(String name, Country country) {
        this.name = name;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


}
