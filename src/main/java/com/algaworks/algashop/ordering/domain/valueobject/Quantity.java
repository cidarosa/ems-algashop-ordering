package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_MINOR_ONE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_NULL;

public record Quantity(Integer value) implements Comparable<Quantity> {

    public static final Quantity ZERO = new Quantity(0);

    public Quantity(Integer value) {

        Objects.requireNonNull(value, VALIDATION_ERROR_QUANTITY_IS_NULL);

        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_IS_MINOR_ONE);
        }

        this.value = value;
    }

    public Quantity add(Integer value){
        return add(new Quantity(value));
    }

    public Quantity add(Quantity quantity) {

        Objects.requireNonNull(quantity);

        if (quantity.value() < 0) {
            throw new IllegalArgumentException();
        }
        return new Quantity(this.value + quantity.value());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Quantity other) {
        return this.value.compareTo(other.value());
    }
}
