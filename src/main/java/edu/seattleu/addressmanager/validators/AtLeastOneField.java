package edu.seattleu.addressmanager.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneFieldValidator.class)
@Documented
public @interface AtLeastOneField {
    String message() default "At least one search field must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
