package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class OrderCannotPlacedException extends DomainException {

    public OrderCannotPlacedException(String message) {
        super(message);
    }

    public static OrderCannotPlacedException noItem(OrderId id) {
        return new OrderCannotPlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, id));
    }

    public static OrderCannotPlacedException noShippingInfo(OrderId id) {
        return new OrderCannotPlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO, id));
    }

    public static OrderCannotPlacedException noBillingInfo(OrderId id) {
        return new OrderCannotPlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, id));
    }

    public static OrderCannotPlacedException noPaymentMethod(OrderId id) {
        return new OrderCannotPlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, id));
    }
}
