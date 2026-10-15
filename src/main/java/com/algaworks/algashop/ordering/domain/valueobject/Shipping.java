package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

@Builder(toBuilder = true) //converter em outro builder já populado com as mesmas propriedades
// e por modifica-las
public record Shipping(Money cost, LocalDate expectedDate, Recipient recipient, Address address
) {
    public Shipping {
        Objects.requireNonNull(cost);
        Objects.requireNonNull(expectedDate);
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(address);
    }
}
