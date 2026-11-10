package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class OrderCancelTest {

    @Test
    void givenDraftOrder_whenCancel_shouldBeCanceled() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.DRAFT)
        );

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.calceledAt()).isBeforeOrEqualTo(OffsetDateTime.now()),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }

    @Test
    void givenPlacedOrder_whenCancel_shouldBeCanceled() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.PLACED)
        );

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.calceledAt()).isBeforeOrEqualTo(OffsetDateTime.now()),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }

    @Test
    void givenPlaidOrder_whenCancel_shouldBeCanceled() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.PAID)
        );

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.calceledAt()).isBeforeOrEqualTo(OffsetDateTime.now()),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }

    @Test
    void givenReadyOrder_whenCancel_shouldBeCanceled() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isFalse(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.READY));

        order.cancel();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.calceledAt()).isBeforeOrEqualTo(OffsetDateTime.now()),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }

    @Test
    void givenCanceledOrder_whenTryCancel_shouldThrowException() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::cancel);

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.isCanceled()).isTrue(),
                (o) -> Assertions.assertThat(o.calceledAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.CANCELED));
    }
}

