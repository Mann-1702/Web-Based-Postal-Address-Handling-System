package edu.seattleu.addressmanager.validators;

import edu.seattleu.addressmanager.model.SearchRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneFieldValidator  implements ConstraintValidator<AtLeastOneField, SearchRequest> {

    @Override
    public void initialize(AtLeastOneField constraintAnnotation) {
        // no-op
    }

    @Override
    public boolean isValid(SearchRequest request, ConstraintValidatorContext context) {
        // Check if all fields are null/empty
        boolean allEmpty = true;

        if (request.getAddress01() != null && !request.getAddress01().isBlank()) {
            allEmpty = false;
        }
        if (request.getAddress02() != null && !request.getAddress02().isBlank()) {
            allEmpty = false;
        }
        if (request.getPostalCode() != null && !request.getPostalCode().isBlank()) {
            allEmpty = false;
        }
        if (request.getCityId() != null) {
            allEmpty = false;
        }
        if (request.getStateId() != null) {
            allEmpty = false;
        }
        if (request.getCountryId() != null) {
            allEmpty = false;
        }

        return !allEmpty; // valid if at least one field is non-empty
    }
}