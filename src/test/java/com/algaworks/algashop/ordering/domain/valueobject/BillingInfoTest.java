package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.entity.BillingInfoTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;

class BillingInfoTest {

    @Test
    void given_validBillingInfo_shouldGenerateNewBillingInfo() {

//        BillingInfo billingInfo = BillingInfoTestDataBuilder.newBillingInfo().build();
//
//
//        assertWith(billingInfo,
//                b -> assertThat(b.fullName()).isEqualTo(new FullName("John", "Snow")),
//                b -> assertThat(b.document()).isEqualTo(new Document("255-08-0578")),
//                b -> assertThat(b.phone()).isEqualTo(new Phone("478-256-2504")),
//                b -> assertThat(b.address()).isEqualTo(
//                        Address.builder()
//                                .street("Bourbon Street")
//                                .number("1134")
//                                .neighborhood("North Ville")
//                                .city("York")
//                                .state("South California")
//                                .zipCode(new ZipCode("12345"))
//                                .complement("Apt. 114")
//                                .build()
//                )
//        );
    }

    @Test
    void given_nullFullName_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfoTestDataBuilder.newBillingInfo()
                        .fullName(new FullName(null, null)).build());
    }

    @Test
    void given_emptyFullName_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BillingInfoTestDataBuilder.newBillingInfo()
                        .fullName(new FullName("", "")).build());
    }

    @Test
    void given_nullAtributs_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfoTestDataBuilder.newBillingInfo()
                        .fullName(new FullName(null, null))
                        .document(new Document(null))
                        .phone(new Phone(null))
                        .build());
    }

    @Test
    void given_demptyAtributs_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> BillingInfoTestDataBuilder.newBillingInfo()
                        .fullName(new FullName("", ""))
                        .document(new Document(""))
                        .phone(new Phone(""))
                        .build());
    }


}