package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.BillingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.ShippingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class OrderTest {

    @Test
    void shouldGenerate() {

        Order order = Order.draft(new CustomerId());
    }

    @Test
    void souldAddItem() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Moiuse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.productName()).isEqualTo(new ProductName("Moiuse pad")),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    void shouldGenerateExceptionsWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Moiuse pad"),
                new Money("100"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();

        order.addItem(
                productId,
                new ProductName("Moiuse pad"),
                new Money("100"),
                new Quantity(2)
        );

        order.addItem(
                productId,
                new ProductName("RAM Memory"),
                new Money("50"),
                new Quantity(1)
        );

        Set<OrderItem> items = order.items();

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("250"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(3));
    }

    @Test
    void givenDraftOrder_whenPlace_shouldChangeToPlace() {

        Order order = OrderTestDataBuilder.anOrder().build();
//        Order order = Order.draft(new CustomerId());
        order.place();
        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    void givenPlacedOrder_whenMarkPaid_shouldChangeToPaid() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();

        Assertions.assertThat(order.isPaid()).isTrue();
        Assertions.assertThat(order.paidAt()).isNotNull();
    }

    @Test
    void givenPlaceOrder_whenTryToPlace_shouldGenerateException() {

        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::place);
    }

    @Test
    void giveDraftOrder_whenChangePaymentMethod_shouldAllowChange() {

        Order order = Order.draft(new CustomerId());
        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);
        Assertions.assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    void giveDraftOrder_whenChangeBillingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .complement("apt. 11")
                .neighbordhood("North Ville")
                .city("Montfort")
                .state("South Caroline")
                .zipCode(new ZipCode("79911")).build();

        BillingInfo billingInfo = BillingInfo.newBilling()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .address(address)
                .build();

        Order order = Order.draft(new CustomerId());
        order.changeBilling(billingInfo);

        BillingInfo expectedBillingInfo
                = BillingInfo.newBilling()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .address(address)
                .build();

        Assertions.assertThat(order.billing()).isEqualTo(expectedBillingInfo);
    }

    @Test
    void givenOrderDraft_whenChangeShippingInfo_shouldAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .complement("apt. 11")
                .neighbordhood("North Ville")
                .city("Montfort")
                .state("South Caroline")
                .zipCode(new ZipCode("79911")).build();

        ShippingInfo shippingInfo = ShippingInfo.newShipping()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .address(address)
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedFeliveryDate = LocalDate.now().plusDays(1);
        order.changeShipping(shippingInfo, shippingCost, expectedFeliveryDate);

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.shipping()).isEqualTo(shippingInfo),
                o -> Assertions.assertThat(o.shippingCost()).isEqualTo(shippingCost),
                o -> Assertions.assertThat(o.expectedDeliveryDate()).isEqualTo(expectedFeliveryDate)
        );
    }

    @Test
    void givenOrderDraftAndDeliveryDateInThePast_whenTryChangeShippingInfo_shouldNotAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .complement("apt. 11")
                .neighbordhood("North Ville")
                .city("Montfort")
                .state("South Caroline")
                .zipCode(new ZipCode("79911")).build();

        ShippingInfo shippingInfo = ShippingInfo.newShipping()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .address(address)
                .build();

        Order order = Order.draft(new CustomerId());
        Money shippingCost = Money.ZERO;
        LocalDate expectedFeliveryDate = LocalDate.now().minusDays(2);

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() -> order.changeShipping(shippingInfo, shippingCost, expectedFeliveryDate));

    }

    @Test
    void givenDraftOrder_whenChangeItem_shoudRecalculate() {

        Order order = Order.draft(new CustomerId());
        order.addItem(
                new ProductId(),
                new ProductName("Desktop X11"),
                new Money("10.00"),
                new Quantity(3)
        );

        OrderItem orderItem = order.items().iterator().next();
        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("50.00")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(5))
        );
    }


}