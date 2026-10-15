package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.Recipient;
import com.algaworks.algashop.ordering.domain.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;

import java.time.LocalDate;

public class ShippingTestDataBuilder {

    public ShippingTestDataBuilder() {
    }

    public static Shipping newShipping() {

        return Shipping.builder()
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .recipient(Recipient.builder()
                        .fullName(new FullName("John", "Snow"))
                        .document(new Document("255-08-0578"))
                        .phone(new Phone("478-256-2504"))
                        .build())
                .address(anAddres()).build();
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
}
