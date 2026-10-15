package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.BillingInfo;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;

public class BillingInfoTestDataBuilder {

    public BillingInfoTestDataBuilder() {
    }

    public static BillingInfo.NewBillingInfoBuild newBillingInfo() {

        return BillingInfo.newBilling()
                .fullName(new FullName("John", "Snow"))
                .document(new Document("255-08-0578"))
                .phone(new Phone("478-256-2504")).
                address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build());
    }
}
