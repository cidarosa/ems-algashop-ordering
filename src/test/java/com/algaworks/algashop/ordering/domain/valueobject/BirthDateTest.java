package com.algaworks.algashop.ordering.domain.valueobject;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class BirthDateTest {

    @Test
    void shouldCalculatedAgre() {

        BirthDate birthDate = new BirthDate(LocalDate.of(1991, 7, 5));

        Integer calc = birthDate.age();
        Assertions.assertThat(calc).isEqualTo(35);
    }


    @Test
    void shouldGenerateWithValidDate() {

        BirthDate birthDate = new BirthDate(LocalDate.of(1991, 7, 5));

        Assertions.assertThat(birthDate.value()).isEqualTo(LocalDate.of(1991, 7, 5));

    }

    @Test
    void given_futureDate_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(LocalDate.of(2027, 7, 5)));
    }

}