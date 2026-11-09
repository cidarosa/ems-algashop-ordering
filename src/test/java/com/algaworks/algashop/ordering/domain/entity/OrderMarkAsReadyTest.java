package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

public class OrderMarkAsReadyTest {

    @Test
    void givenPaidOrder_whenCallAskReady_shouldUpdateStatus() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        order.markAsReady();

        Assertions.assertWith(order,
               (o) -> Assertions.assertThat(o.isPaid()).isFalse(),
                (o) -> Assertions.assertThat(o.isReady()).isTrue(),
                (o) -> Assertions.assertThat(o.readyAt()).isNotNull(),
                (o) -> Assertions.assertThat(o.readyAt()).isBeforeOrEqualTo(OffsetDateTime.now()),
                (o) -> Assertions.assertThat(o.status()).isEqualTo(OrderStatus.READY));
    }

    @Test
    void givenDraftOrder_whenTryMarkAsReady_shouldTryException(){

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.DRAFT);
        Assertions.assertThat(order.readyAt()).isNull();
    }

    @Test
    void givenPlaceOrder_whenTryMarkAsReady_shouldTryException(){

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.PLACED);
        Assertions.assertThat(order.readyAt()).isNull();
    }

    @Test
    void givenReadyOrder_whenTryMarkAsReady_shouldTryException(){

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.READY);
        Assertions.assertThat(order.readyAt()).isNotNull();
    }
}
