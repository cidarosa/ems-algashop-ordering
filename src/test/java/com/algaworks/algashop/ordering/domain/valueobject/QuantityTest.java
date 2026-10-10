package com.algaworks.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void given_validQuantity_shouldGenerateValue() {

        Quantity quantity = new Quantity(0);

        Assertions.assertThat(quantity.value()).isEqualTo(0);
    }

    @Test
    void given_nullQuantity_shouldGenerateException(){

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Quantity(null));
    }

    @Test
    void given_negativeQuantity_shouldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(-1));
    }

    @Test
    void given_validQuantity_shouldAddValue(){

        Quantity quantity = new Quantity(10);

        Assertions.assertThat(quantity.add(10).value()).isEqualTo(20);
    }

}