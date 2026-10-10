package com.algaworks.algashop.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MONEY_IS_NEGATIVE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MONEY_IS_NULL;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_MINOR_ONE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_NULL;

public record Money(BigDecimal value) implements Comparable<Money> {

    private static final RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {

        Objects.requireNonNull(value, VALIDATION_ERROR_MONEY_IS_NULL);

        if (value == null || value.isBlank()){
            throw new IllegalArgumentException();
        }

        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {

        Objects.requireNonNull(value, VALIDATION_ERROR_MONEY_IS_NULL);

        this.value = value.setScale(2, roundingMode);

        if (this.value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_MONEY_IS_NEGATIVE);
        }
    }

    public Money add(Money money) {

        Objects.requireNonNull(money, VALIDATION_ERROR_MONEY_IS_NULL);

        return new Money(this.value.add(money.value));
    }

    public Money multiply(Quantity quantity) {

        Objects.requireNonNull(quantity, VALIDATION_ERROR_QUANTITY_IS_NULL);

        if (quantity.value() < 1) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_IS_MINOR_ONE);
        }

        BigDecimal totalMultiplied = this.value.multiply(new BigDecimal(quantity.value()));

        return new Money(totalMultiplied);
    }

    public Money divide(Money money) {

        Objects.requireNonNull(money, VALIDATION_ERROR_MONEY_IS_NULL);

        if (money.value.compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException();
        }
        return new Money(this.value.divide(money.value, 2, roundingMode));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(Money o) {
        return this.value().compareTo(o.value());
    }
}
