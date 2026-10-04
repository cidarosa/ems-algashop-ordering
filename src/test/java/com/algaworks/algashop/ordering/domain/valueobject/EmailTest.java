package com.algaworks.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void shouldAddAValidEmail(){

        Email email = new Email("john.snow@gmail.com");

        Assertions.assertThat(email.value()).isEqualTo("john.snow@gmail.com");

    }
    @Test
    void shouldNotAddInvalidEmail(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("jon.gmail.com"));
    }

    @Test
    void shouldNotAddEmailIsBlank(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email(""));
    }
}