package edu.seattleu.addressmanager.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "SearchRequest", description = "Represents address Search request")
public class SearchRequest {

    @NotNull
    @Size(max = 255, message = "address01 cannot exceed 255 characters")
    @Schema(description = "partial address Request by address01", example = "123 Main St")
    private String address01;

    @Size(max = 255, message = "address02 cannot exceed 255 characters")
    @Schema(description = "partial address Request by address02", example = "Unit 4")
    private String address02;

    @Size(max = 20, message = "postalCode cannot exceed 20 characters")
    @Schema(description = "partial postal code match", example = "98101")
    private String postalCode;

    @Min(value = 1, message = "cityId must be a positive number if provided")
    @Schema(description = "Filter by City ID", example = "12")
    private Long cityId;

    @Min(value = 1, message = "stateId must be a positive number if provided")
    @Schema(description = "Filter by State/Province ID", example = "8")
    private Long stateId;

    @Min(value = 1, message = "countryId must be a positive number if provided")
    @Schema(description = "Filter by Country ID", example = "3")
    private Long countryId;

    public SearchRequest() {
    }

    public SearchRequest(String address01, String address02,
                         String postalCode, Long cityId,
                         Long stateId, Long countryId) {
        this.address01 = address01;
        this.address02 = address02;
        this.postalCode = postalCode;
        this.cityId = cityId;
        this.stateId = stateId;
        this.countryId = countryId;
    }


}