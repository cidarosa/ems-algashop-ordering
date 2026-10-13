package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderCannotPlacedException extends DomainException {

    public OrderCannotPlacedException(OrderId id) {
        super(String.format(ErrorMessages.ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, id));
    }
}
