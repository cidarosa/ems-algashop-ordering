package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void given_documentValid_shouldCreateDocument() {

        Document document = new Document("255-08-0578");
        Assertions.assertThat(document.value()).isEqualTo("255-08-0578");
    }

    @Test
    void given_blankDocument_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document(""));
    }

    @Test
    void given_emptyDocument_souldGenerateException() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document("    "));
    }

    @Test
    void given_nullDocument_shouldGenerateException() {

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Document(null));
    }
}