package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.exception.OrderDoesNotContainOrderItemException;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderRemoveItemTest {

    @Test
    void givenDraftOrder_whenRemoveItemIsCalled_shouldRemoveItemAndRecalculate() {

        Order order = Order.draft(new CustomerId());

        Product product = ProductTestDataBuilder.aProduct().build();
        order.addItem(product, new Quantity(2));
        OrderItem aProduct = order.items().iterator().next();

        Product productRam = ProductTestDataBuilder.aProductAltRamMemory().build();
        order.addItem(productRam, new Quantity(1));
        OrderItem aProductRam = order.items().iterator().next();

        Assertions.assertWith(order,
                o -> Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3)),
                o -> Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("6200"))
        );

        order.removeItem(aProduct.id());

        Assertions.assertWith(order,
                o -> Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(1)),
                o -> Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("200"))
        );
    }

    @Test
    void givenDraftOrder_whenTryToRemoveNoExistingItem_shouldThrowException() {

        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(() -> order.removeItem(new OrderItemId()));

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("6210")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(3)));
    }

    @Test
    void givenPlaceOrder_whenTryRemoveItem_shouldThrowException() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.removeItem(new OrderItemId()));

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("6210")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(3)));
    }
}
