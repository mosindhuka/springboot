package com.example.myfirstapp.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class DobValidator implements ConstraintValidator<ValidDob, LocalDate> {

    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {

        if (dob == null) {
            return true; // Let @NotNull handle nulls
        }

        if (dob.isAfter(LocalDate.now())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Date of birth cannot be in the future")
                    .addConstraintViolation();
            return false;
        }

        if (Period.between(dob, LocalDate.now()).getYears() < 18) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Age must be at least 18 years")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}