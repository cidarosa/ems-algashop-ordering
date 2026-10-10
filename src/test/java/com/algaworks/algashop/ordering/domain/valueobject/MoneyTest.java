package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class MoneyTest {

    @Test
    void given_validMoneyValue_shouldGenerateValue() {

        Money money = new Money(BigDecimal.valueOf(10.0));
        Assertions.assertThat(money.value().compareTo(BigDecimal.valueOf(10.0)));
    }

    @Test
    void given_validValue_should_GenerateValueWhithHalfEven() {

        Money money = new Money(BigDecimal.valueOf(10.0259));
        Assertions.assertThat(money.value()).isEqualTo(BigDecimal.valueOf(10.03));
    }

    @Test
    void given_negativeMoneyValue_should_GenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(BigDecimal.valueOf(-10.25)));
    }

    @Test
    void given_nullMoneyValue_should_GenerateException() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money((BigDecimal) null));
    }

    @Test
    void given_validMoneyValueAndValueQuantity_should_MultiplyAndReturnNewValue() {

        Quantity quantity = new Quantity(5);
        Money money = new Money(BigDecimal.valueOf(10.0));

        Assertions.assertThat(money.multiply(quantity)).isEqualTo(new Money(BigDecimal.valueOf(50.0)));
    }

    @Test
    void given_validMoneyValueAndInvalidValueQuantity_should_GenerateException() {

        Money money = new Money(BigDecimal.valueOf(10.0));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> money.multiply(new Quantity(-5)));
    }

    @Test
    void given_validMoneyValues_should_divideAndReturnHalfEvenValue() {

        Money value = new Money(BigDecimal.valueOf(10.595));
        Money money = new Money(BigDecimal.valueOf(2));

        Assertions.assertThat(value.divide(money)).isEqualTo(new Money(BigDecimal.valueOf(5.30)));

    }

    @Test
    void given_invalidValueToDivideForZero_should_GenerateException() {

        Money value = new Money(BigDecimal.valueOf(10.595));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> value.divide(new Money(BigDecimal.ZERO)));
    }

}