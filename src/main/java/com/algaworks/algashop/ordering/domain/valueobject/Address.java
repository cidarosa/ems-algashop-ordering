package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;


public record Address(
        String street,
        String number,
        String complement,
        String neighbordhood,
        String city,
        String state,
        ZipCode zipCode
) {
    @Builder(toBuilder = true) //passa builder populado
    public Address {

        FieldValidations.requireNonBlank(street);
        FieldValidations.requireNonBlank(number);
        FieldValidations.requireNonBlank(neighbordhood);
        FieldValidations.requireNonBlank(city);
        FieldValidations.requireNonBlank(state);

        Objects.requireNonNull(zipCode);
    }
}
