package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderIsCanceledTest {

    @Test
    void givenCanceledOrder_whenIsCanceled_shouldReturnTrue() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.DRAFT));

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }

    @Test
    void givenOrderInAnyOtherStatus_whenIsCanceled_shouldReturnFalse() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("6210")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(3)),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.PAID));
    }
}
