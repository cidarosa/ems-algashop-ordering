package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.BillingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.Recipient;
import com.algaworks.algashop.ordering.domain.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();

    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

//    private Money shippingCost = new Money("10.00");
//    private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);

    private Shipping shipping = aShipping();
    private BillingInfo billingInfo = aBillingInfo();

    private boolean withItems = true;

    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {

    }

    public Order build() {

        Order order = Order.draft(customerId);
        order.changeShipping(shipping);
        order.changeBilling(billingInfo);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));
            order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        }

        switch (this.status) {
            case DRAFT -> {
            }
            case PLACED -> {
                order.place();
            }
            case PAID -> {
                order.place();
                ;
                order.markAsPaid();
            }
            case READY -> {

            }
            case CANCELED -> {

            }
        }
        return order;
    }

    //setters - customizados na IDE
    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shippingInfo(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    //

    public static Shipping aShipping() {

        return Shipping.builder()
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .recipient(Recipient.builder()
                        .fullName(new FullName("John", "Snow"))
                        .document(new Document("225-09-1992"))
                        .phone(new Phone("123-111-9911"))
                        .build())
                .address(anAddres())
                .build();
    }

    public static BillingInfo aBillingInfo() {

        return BillingInfo.newBilling()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("225-09-1992"))
                .phone(new Phone("123-111-9911"))
                .address(anAddres())
                .build();
    }

    public static Address anAddres() {

        return Address.builder()
                .street("Bourbon Street")
                .number("1234")
                .complement("apt. 11")
                .neighborhood("North Ville")
                .city("Montfort")
                .state("South Caroline")
                .zipCode(new ZipCode("79911")).build();
    }

    public static Shipping aShippingAlt() {

        return Shipping.builder()
                .cost(new Money("200"))
                .expectedDate(LocalDate.now().plusWeeks(2))
                .recipient(Recipient.builder()
                        .fullName(new FullName("Mary", "Jones"))
                        .document(new Document("552-11-4333"))
                        .phone(new Phone("54-454-1144"))
                        .build())
                .address(anAddresAlt())
                .build();
    }


    public static Address anAddresAlt() {

        return Address.builder()
                .street("Sansome Street")
                .number("875")
                .complement("apt. 11")
                .neighborhood("Sansome")
                .city("San Francisco")
                .state("Californai")
                .zipCode(new ZipCode("08040")).build();
    }

}
