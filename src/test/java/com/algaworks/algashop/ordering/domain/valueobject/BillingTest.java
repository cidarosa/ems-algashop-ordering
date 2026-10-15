package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.entity.OrderTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;

class BillingTest {

    @Test
    void given_validBilling_shouldGenerateNewBilling() {

        Billing billing = OrderTestDataBuilder.aBilling();

        assertWith(billing,
                b -> assertThat(b.fullName()).isEqualTo(new FullName("John", "Snow")),
                b -> assertThat(b.document()).isEqualTo(new Document("225-09-1992")),
                b -> assertThat(b.phone()).isEqualTo(new Phone("123-111-9911")),
                b -> assertThat(b.email()).isEqualTo(new Email("john.snow@gmail.com")),
                b -> assertThat(b.address()).isEqualTo(OrderTestDataBuilder.anAddres())
        );
    }

    @Test
    void given_nullFullName_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(null)
                        .build());
    }

    @Test
    void given_emptyFullName_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("", "")).build());
    }

    @Test
    void given_nullAtributs_shouldGenereateException() {

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName(null, null))
                        .document(new Document(null))
                        .phone(new Phone(null))
                        .email(new Email(null))
                        .build());
    }

    @Test
    void given_emptyAtributs_shouldGenereateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("", ""))
                        .document(new Document(""))
                        .phone(new Phone(""))
                        .email(new Email("  "))
                        .build());
    }
}