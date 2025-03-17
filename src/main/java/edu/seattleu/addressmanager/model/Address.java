package edu.seattleu.addressmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
@Schema(name = "Address", description = "Represents an address within a city")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the address", example = "1001")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Address01", example = "123 Main St")
    @JsonProperty("address01")
    private String address01;



    @Schema(description = "Address02", example = "123 Main St")
    @JsonProperty("address02")
    private String address02;

    @Schema(description = "Postal code of the address", example = "98101")
    @JsonProperty("postalCode")
    private String postalCode;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @Schema(description = "Associated city")
    @JsonIgnore // Exclude the entire country object from JSON
    @JsonIgnoreProperties({"state", "addresses"})
    private City city;

    @JsonProperty("cityId")
    public Long getCityId() {
        return city != null ? city.getId() : null;
    }

    @JsonProperty("cityName")
    public String getCityName() {
        return city != null ? city.getName(): null;
    }

    @JsonProperty("stateId")
    public Long getStatId() {
        return city != null ? city.getStateId() : null;
    }

    @JsonProperty("stateName")
    public String getStateName() {
        return city != null ? city.getState().getName(): null;
    }

    @JsonProperty("countryId")
    public Long getCountryId() {
        return city != null ? city.getCountryId() : null;
    }

    @JsonProperty("CountryName")
    public String getCountryName() {
        return city != null ? city.getState().getCountry().getName(): null;
    }



    public Address() {
        // Default no-args constructor
    }

    public Address(String address01, String address02,String postalCode, City city) {
        this.address01 = address01;
        this.address02 = address02;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress01() {
        return address01;
    }

    public void setAddress01(String street) {
        this.address01 = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress02() {
        return address02;
    }

    public void setAddress02(String address02) {
        this.address02 = address02;
    }

}
