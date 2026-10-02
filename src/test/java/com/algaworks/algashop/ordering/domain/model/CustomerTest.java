package com.algaworks.algashop.ordering.domain.model;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

class CustomerTest {

    @Test
    public void testingCustomer(){

        Customer customer = new Customer(
                IdGenerator.generateTimBasedUUID(),
                "John Snow",
                LocalDate.of(1991, 7,5),
                "johsnow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                true,
                OffsetDateTime.now()
        );
        customer.addLoyaltPoint(10);

        System.out.println(customer.id());
        System.out.println(IdGenerator.generateTimBasedUUID());
    }



}