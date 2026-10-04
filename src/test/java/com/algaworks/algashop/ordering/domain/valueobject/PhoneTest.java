package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void given_validPhone_shouldCreatePhone() {

        Phone phone = new Phone("478-256-2504");

        Assertions.assertThat(phone.value()).isEqualTo("478-256-2504");
    }

    @Test
    void given_blankPhone_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone(""));
    }

    @Test
    void given_emptyPhone_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Phone("   "));
    }

    @Test
    void given_nullPhone_shouldGenerateException() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Phone(null));
    }

}