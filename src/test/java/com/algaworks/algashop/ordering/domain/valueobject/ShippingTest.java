package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.entity.ShippingTestDataBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;


class ShippingTest {

    @Test
    void given_validShippingInfo_shouldGenerateNewShippingInfo() {

        Shipping shipping = ShippingTestDataBuilder.newShipping();

        assertWith(shipping,
                sh -> assertThat(sh.cost().equals(new Money("10"))),
                sh -> assertThat(sh.expectedDate().equals(LocalDate.now().plusWeeks(1))),
                sh -> assertThat(sh.recipient().fullName()).isEqualTo(new FullName("John", "Snow")),
                sh -> assertThat(sh.recipient().document()).isEqualTo(new Document("255-08-0578")),
                sh -> assertThat(sh.recipient().phone()).isEqualTo(new Phone("478-256-2504")),
                sh -> assertThat(sh.address()).isEqualTo(
                        Address.builder()
                                .street("Bourbon Street")
                                .number("1234")
                                .complement("apt. 11")
                                .neighbordhood("North Ville")
                                .city("Montfort")
                                .state("South Caroline")
                                .zipCode(new ZipCode("79911")).build())
        );
    }

//    @Test
//    void given_nullFullName_shouldGenereateException() {
//
//
//  }

//    @Test
//    void given_emptyFullName_shouldGenereateException() {
//
//        assertThatExceptionOfType(IllegalArgumentException.class)
//                .isThrownBy(() -> ShippingTestDataBuilder.newShipping()
//                        .fullName(new FullName("", "")).build());
//    }
//
//    @Test
//    void given_nullAtributs_shouldGenereateException() {
//
//        assertThatExceptionOfType(NullPointerException.class)
//                .isThrownBy(() -> ShippingTestDataBuilder.newShipping()
//                        .fullName(new FullName(null, null))
//                        .document(new Document(null))
//                        .phone(new Phone(null))
//                        .build());
//    }
//
//    @Test
//    void given_demptyAtributs_shouldGenereateException() {
//
//        assertThatExceptionOfType(IllegalArgumentException.class)
//                .isThrownBy(() -> ShippingTestDataBuilder.newShipping()
//                        .fullName(new FullName("", ""))
//                        .document(new Document(""))
//                        .phone(new Phone(""))
//                        .build());
//    }
}