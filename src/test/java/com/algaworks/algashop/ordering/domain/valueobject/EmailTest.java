package com.algaworks.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void given_validEmail_shouldCreateEmail(){

        Email email = new Email("john.snow@gmail.com");

        Assertions.assertThat(email.value()).isEqualTo("john.snow@gmail.com");

    }
    @Test
    void given_invalidEmail_souldGenerateException(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("jon.gmail.com"));
    }

    @Test
    void given_blankEmail_souldGenerateException(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email(""));
    }

     @Test
    void given_emptyEmail_souldGenerateException(){

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Email("   "));
    }


}