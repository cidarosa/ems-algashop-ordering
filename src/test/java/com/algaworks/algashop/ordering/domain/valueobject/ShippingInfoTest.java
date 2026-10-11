package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.entity.ShippingInfoTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;


class ShippingInfoTest {

    @Test
    void given_validShippingInfo_shouldGenerateNewShippingInfo() {

        ShippingInfo shippingInfo = ShippingInfoTestDataBuilder.newShippingInfo().build();

        assertWith(shippingInfo,
                sh -> assertThat(sh.fullName()).isEqualTo(new FullName("John", "Snow")),
                sh -> assertThat(sh.document()).isEqualTo(new Document("255-08-0578")),
                sh -> assertThat(sh.phone()).isEqualTo(new Phone("478-256-2504")),
                sh -> assertThat(sh.address()).isEqualTo(
                        Address.builder()
                                .street("Bourbon Street")
                                .number("1134")
                                .neighbordhood("North Ville")
                                .city("York")
                                .state("South California")
                                .zipCode(new ZipCode("12345"))
                                .complement("Apt. 114")
                                .build())

        );
    }

    @Test
    void given_nullFullName_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> ShippingInfoTestDataBuilder.newShippingInfo()
                        .fullName(new FullName(null, null)).build());
    }

    @Test
    void given_emptyFullName_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ShippingInfoTestDataBuilder.newShippingInfo()
                        .fullName(new FullName("", "")).build());
    }

    @Test
    void given_nullAtributs_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> ShippingInfoTestDataBuilder.newShippingInfo()
                        .fullName(new FullName(null, null))
                        .document(new Document(null))
                        .phone(new Phone(null))
                        .build());
    }

    @Test
    void given_demptyAtributs_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ShippingInfoTestDataBuilder.newShippingInfo()
                        .fullName(new FullName("", ""))
                        .document(new Document(""))
                        .phone(new Phone(""))
                        .build());
    }
}