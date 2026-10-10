package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @Test
    void given_validProductName_shouldGenerateProductName() {

        ProductName productName = new ProductName("Curso de Microservices");

        Assertions.assertThat(productName.value()).isEqualTo("Curso de Microservices");
    }

    @Test
    void given_nullProductName_souldGenerateException() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ProductName(null));
    }

    @Test
    void given_emptyProductName_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(""));
    }


}