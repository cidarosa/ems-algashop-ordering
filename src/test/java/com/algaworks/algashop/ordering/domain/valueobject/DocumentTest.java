package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void shouldAddDocument() {

        Document document = new Document("255-08-0578");

        Assertions.assertThat(document.value()).isEqualTo("255-08-0578");
    }

    @Test
    void shouldNotAddDocumentIsBlank() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Document(""));

    }

       @Test
    void shouldNotAddDocumentIsNull() {

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> new Document(null));

    }

}