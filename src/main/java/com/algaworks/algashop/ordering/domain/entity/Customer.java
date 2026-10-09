package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.Address;
import com.algaworks.algashop.ordering.domain.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.Email;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_NULL;

public class Customer {

    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;

    private Address address;

    // novo cliente
    // Static Factory Method
    public static Customer brandNew(FullName fullName, BirthDate birthDate,
                                    Email email, Phone phone, Document document,
                                    Boolean promotionNotificationAllowed,
                                    Address address) {
        return new Customer(
                new CustomerId(),
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationAllowed,
                false,
                OffsetDateTime.now(),
                null,
                LoyaltyPoints.ZERO,
                address);
    }

//    public Customer(CustomerId id, FullName fullName, BirthDate birthDate,
//                    Email email, Phone phone, Document document,
//                    Boolean promotionNotificationAllowed,
//                    OffsetDateTime registeredAt, Address address) {
//        this.setId(id);
//        this.setFullName(fullName);
//        this.setBirthDate(birthDate);
//        this.setEmail(email);
//        this.setPhone(phone);
//        this.setDocument(document);
//        this.setPromotionNotificationAllowed(promotionNotificationAllowed);
//        this.setRegisteredAt(registeredAt);
//        this.setArchived(false);
//        this.setLoyaltyPoints(LoyaltyPoints.ZERO);
//        this.setAddress(address);
//    }

    //cliente existente
    // Static Factory Method
    public static Customer existing(CustomerId id, FullName fullName, BirthDate birthDate,
                                    Email email, Phone phone,
                                    Document document,
                                    Boolean promotionNotificationAllowed,
                                    Boolean archived, OffsetDateTime registeredAt,
                                    OffsetDateTime archivedAt,
                                    LoyaltyPoints loyaltyPoints,
                                    Address address) {
        return new Customer(
                id,
                fullName,
                birthDate,
                email,
                phone,
                document,
                promotionNotificationAllowed,
                archived,
                registeredAt,
                archivedAt,
                loyaltyPoints,
                address
        );
    }

    private Customer(CustomerId id, FullName fullName, BirthDate birthDate,
                    Email email, Phone phone,
                    Document document,
                    Boolean promotionNotificationAllowed,
                    Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt,
                    LoyaltyPoints loyaltyPoints,
                    Address address) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationAllowed(promotionNotificationAllowed);
        this.setArchived(archived);
        this.setRegisteredAt(registeredAt);
        this.setArchivedAt(archivedAt);
        this.setLoyaltyPoints(loyaltyPoints);
        this.setAddress(address);
    }


    public void addLoyaltyPoints(LoyaltyPoints loyaltyPointsAdded) {

        verifyIfChangeble();
        this.setLoyaltyPoints(this.loyaltyPoints().add(loyaltyPointsAdded));
    }

    public void archive() {

        verifyIfChangeble();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setBirthDate(null);
        this.setPromotionNotificationAllowed(false);

//        Address.AddressBuilder addressBuilder = this.address.toBuilder();
//        this.setAddress(addressBuilder.number("Anonymized").complement(null).build());
        this.setAddress(this.address().toBuilder()
                .number("Anonymized")
                .complement(null).
                build());
    }

    public void enablePromotionNotifications() {
        verifyIfChangeble();
        this.setPromotionNotificationAllowed(true);
    }

    public void disablePromotionNotifications() {
        verifyIfChangeble();
        this.setPromotionNotificationAllowed(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeble();
        this.setFullName(fullName);
    }

    public void changeEmail(Email email) {
        verifyIfChangeble();
        this.setEmail(email);
    }

    public void changePhone(Phone phone) {
        verifyIfChangeble();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeble();
        this.setAddress(address);

    }
    //---

    public Document document() {
        return document;
    }

    // metodos getters similar aos records
    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Boolean isPromotionNotificationAllowed() {
        return promotionNotificationAllowed;
    }

    public Boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    public Address address() {
        return address;
    }

    private void setId(CustomerId id) {

        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_NULL);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {

        if (birthDate == null) {
            this.birthDate = null;
            return;
        }

        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {

        this.email = email;
    }

    private void setPhone(Phone phone) {

        Objects.requireNonNull(phone);

        this.phone = phone;
    }

    private void setDocument(Document document) {

        this.document = document;
    }

    private void setPromotionNotificationAllowed(Boolean promotionNotificationAllowed) {

        Objects.requireNonNull(promotionNotificationAllowed);

        this.promotionNotificationAllowed = promotionNotificationAllowed;
    }

    private void setArchived(Boolean archived) {

        Objects.requireNonNull(archived);

        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {

        Objects.requireNonNull(registeredAt);

        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {

        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {

        Objects.requireNonNull(loyaltyPoints);

        this.loyaltyPoints = loyaltyPoints;
    }

    private void setAddress(Address address) {

        Objects.requireNonNull(address);

        this.address = address;
    }

    private void verifyIfChangeble() {
        if (this.isArchived()) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
