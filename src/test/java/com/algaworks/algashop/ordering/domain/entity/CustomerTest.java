package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;

import com.algaworks.algashop.ordering.domain.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.*;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                        () -> {
                            new Customer(
                                    new CustomerId(),
                                    new FullName("John", "Snow"),
                                    new BirthDate(LocalDate.of(1991, 7, 5)),
                                    "invalid",
                                    "478-256-2504",
                                    "255-08-0578",
                                    false,
                                    OffsetDateTime.now()
                            );
                        }
                );
    }

    @Test
    void given_invalidEmail_whenTryUpdateCustomer_shouldGenerateException() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Snow"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                "john.snow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(
                        () -> {
                            customer.changeEmail("invalid");
                        }
                );
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Snow"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                "john.snow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo("john.snow@gmail.com"),
                c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document()).isEqualTo("000-00-0000"),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationAllowed()).isFalse()
        );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                null,
                "anonymous@anonymous.com",
                "000-000-0000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10)
        );

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("email@gmail.com"));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone("123-123-1234"));

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Snow"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                "john.snow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {

        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Snow"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                "john.snow@gmail.com",
                "478-256-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }

}