package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PRODUCTNAME_IS_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PRODUCTNAME_IS_NULL;

public record ProductName(String value) {

    public ProductName(String value) {

        Objects.requireNonNull(value, VALIDATION_ERROR_PRODUCTNAME_IS_NULL);

        FieldValidations.requireNonBlank(value, VALIDATION_ERROR_PRODUCTNAME_IS_BLANK);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
