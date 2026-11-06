package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.Shipping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderChangingTest {

    @Test
    void givenDraftOrder_WhenDataChanged_shouldNotThrowException() {

        Order draftOrder = OrderTestDataBuilder.anOrder().build();

        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();
        Quantity quantity = new Quantity(2);
        Billing billing = OrderTestDataBuilder.aBilling();
        Shipping shipping = OrderTestDataBuilder.aShipping();
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;

        OrderItem orderItem = draftOrder.items().iterator().next();

        Assertions.assertThatCode(() -> draftOrder.addItem(product, quantity)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeBilling(billing)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeShipping(shipping)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changeItemQuantity(orderItem.id(), quantity)).doesNotThrowAnyException();
        Assertions.assertThatCode(() -> draftOrder.changePaymentMethod(paymentMethod)).doesNotThrowAnyException();
    }

    @Test
    void givenPlaceOrder_whenTryAddAnItem_shouldThrowOrderCannotBeEditedException() {

        Order placeOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();
        Quantity quantity = new Quantity(2);

        Assertions.assertThatThrownBy(
                        () -> placeOrder.addItem(product, quantity))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlaceOrder_whenChangeItemQuantityIsCalled_shouldThrowOrderCannotBeEditedException() {

        Order placeOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Quantity quantity = new Quantity(10);

        OrderItem orderItem = placeOrder.items().iterator().next();

        Assertions.assertThatThrownBy(
                        () -> placeOrder.changeItemQuantity(orderItem.id(), quantity))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlaceOrder_whenChangeShippingIsCalled_shouldThrowOrderCannotBeEditedException() {

        Order placeOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Shipping shipping = OrderTestDataBuilder.aShipping();

        Assertions.assertThatThrownBy(
                        () -> placeOrder.changeShipping(shipping))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlaceOrder_whenChangeBillingIsCalled_shouldThrowOrderCannotBeEditedException() {

        Order placeOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Billing billing = OrderTestDataBuilder.aBilling();

        Assertions.assertThatThrownBy(
                        () -> placeOrder.changeBilling(billing))
                .isInstanceOf(OrderCannotBeEditedException.class);
    }

    @Test
    void givenPlaceOrder_whenChangePaymentMethod_shouldThrowOrderCannotBeEditedException() {

        Order placeOrder = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        PaymentMethod method = PaymentMethod.GATEWAY_BALANCE;

        Assertions.assertThatThrownBy(
                        () -> placeOrder.changePaymentMethod(method))
                .isInstanceOf(OrderCannotBeEditedException.class);

    }
}
