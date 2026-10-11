package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.BillingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.ShippingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import lombok.Builder;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")
//aggregate root - pai
public class Order {

    private OrderId id;
    private CustomerId customerId;

    private Money totalAmount;
    private Quantity totalItems;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime calceledAt;
    private OffsetDateTime readyAt;

    private BillingInfo billing;
    private ShippingInfo shipping;

    private OrderStatus status;
    private PaymentMethod paymentMethod;

    private Money shippingCost;
    private LocalDate expectedDeliveyDate;

    private Set<OrderItem> items;

    public Order(OrderId id, CustomerId customerId,
                 Money totalAmount, Quantity totalItems,
                 OffsetDateTime placedAt, OffsetDateTime paidAt,
                 OffsetDateTime calceledAt, OffsetDateTime readyAt,
                 BillingInfo billing, ShippingInfo shipping,
                 OrderStatus status, PaymentMethod paymentMethod,
                 Money shippingCost, LocalDate expectedDeliveyDate,
                 Set<OrderItem> items) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setTotalAmount(totalAmount);
        this.setTotalItems(totalItems);
        this.setPlacedAt(placedAt);
        this.setPaidAt(paidAt);
        this.setCalceledAt(calceledAt);
        this.setReadyAt(readyAt);
        this.setBilling(billing);
        this.setShipping(shipping);
        this.setStatus(status);
        this.setPaymentMethod(paymentMethod);
        this.setShippingCost(shippingCost);
        this.setExpectedDeliveyDate(expectedDeliveyDate);
        this.setItems(items);
    }

    //factory para pedido que não existe
    public static Order draft(CustomerId customerId) {
        return new Order(
                new OrderId(),
                customerId,
                Money.ZERO,
                Quantity.ZERO,
                null,
                null,
                null,
                null,
                null,
                null,
                OrderStatus.DRAFT,
                null,
                null,
                null,
                new HashSet<>()
        );
    }

    // metodos comportamento
    public void addItem(
            ProductId productId,
            ProductName productName,
            Money price,
            Quantity quantity) {

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(this.id)
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .build();

        if (this.items == null){
            this.items = new HashSet<>();
        }

        this.items.add(orderItem);
    }

    //getters - record Style

    public OrderId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public Money totalAmount() {
        return totalAmount;
    }

    public Quantity totalItems() {
        return totalItems;
    }

    public OffsetDateTime placedAt() {
        return placedAt;
    }

    public OffsetDateTime paidAt() {
        return paidAt;
    }

    public OffsetDateTime calceledAt() {
        return calceledAt;
    }

    public OffsetDateTime readyAt() {
        return readyAt;
    }

    public BillingInfo billing() {
        return billing;
    }

    public ShippingInfo shipping() {
        return shipping;
    }

    public OrderStatus status() {
        return status;
    }

    public PaymentMethod paymentMethod() {
        return paymentMethod;
    }

    public Money shippingCost() {
        return shippingCost;
    }

    public LocalDate expectedDeliveyDate() {
        return expectedDeliveyDate;
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(this.items);
    }

    //setters - private

    private void setId(OrderId id) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        Objects.requireNonNull(customerId);
        this.customerId = customerId;
    }

    private void setTotalAmount(Money totalAmount) {
        Objects.requireNonNull(totalAmount);
        this.totalAmount = totalAmount;
    }

    private void setTotalItems(Quantity totalItems) {
        Objects.requireNonNull(totalItems);
        this.totalItems = totalItems;
    }

    private void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    private void setPaidAt(OffsetDateTime paidAt) {
        this.paidAt = paidAt;
    }

    private void setCalceledAt(OffsetDateTime calceledAt) {
        this.calceledAt = calceledAt;
    }

    private void setReadyAt(OffsetDateTime readyAt) {
        this.readyAt = readyAt;
    }

    private void setBilling(BillingInfo billing) {
        this.billing = billing;
    }

    private void setShipping(ShippingInfo shipping) {
        this.shipping = shipping;
    }

    private void setStatus(OrderStatus status) {
        Objects.requireNonNull(status);
        this.status = status;
    }

    private void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private void setShippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
    }

    private void setExpectedDeliveyDate(LocalDate expectedDeliveyDate) {
        this.expectedDeliveyDate = expectedDeliveyDate;
    }

    private void setItems(Set<OrderItem> items) {
        Objects.requireNonNull(items);
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
