package com.algaworks.algashop.ordering.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

class CustomerTest {

    @Test
    public void testingCustomer(){

        Customer customer = new Customer(
                UUID.randomUUID(),
                "John Snow",
                LocalDate.of(1991, 7,5),
                "johsnow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );
        customer.addLoyaltPoint(10);
    }

}