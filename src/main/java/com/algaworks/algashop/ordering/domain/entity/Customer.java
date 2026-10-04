package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;
import com.algaworks.algashop.ordering.domain.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULLNAME_IS_NULL;

public class Customer {

    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate,
                    String email, String phone,
                    String document,
                    Boolean promotionNotificationAllowed,
                    Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt,
                    LoyaltyPoints loyaltyPoints) {
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
    }

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate,
                    String email, String phone, String document,
                    Boolean promotionNotificationAllowed,
                    OffsetDateTime registeredAt) {
        this.setId(id);
        this.setFullName(fullName);
        this.setBirthDate(birthDate);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDocument(document);
        this.setPromotionNotificationAllowed(promotionNotificationAllowed);
        this.setRegisteredAt(registeredAt);
        this.setArchived(false);
        this.setLoyaltyPoints(LoyaltyPoints.ZERO);
    }

    public void addLoyaltyPoints(LoyaltyPoints loyaltyPointsAdded) {

        verifyIfChangeble();

//        if (loyaltyPointsAdded <= 0) {
//            throw new IllegalArgumentException();
//        }
        this.setLoyaltyPoints(this.loyaltyPoints().add( loyaltyPointsAdded));

    }

    public void archive() {

        verifyIfChangeble();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setEmail(UUID.randomUUID() + "@anonymous.com");
        this.setPhone("000-000-0000");
        this.setDocument("000-00-0000");
        this.setBirthDate(null);
        this.setPromotionNotificationAllowed(false);
    }

    public void enablePromotionNotifications() {
        verifyIfChangeble();
        setPromotionNotificationAllowed(true);
    }

    public void disablePromotionNotifications() {
        verifyIfChangeble();
        setPromotionNotificationAllowed(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeble();
        setFullName(fullName);
    }

    public void changeEmail(String email) {
        verifyIfChangeble();
        setEmail(email);
    }

    public void changePhone(String phone) {
        verifyIfChangeble();
        setPhone(phone);
    }

    public String document() {
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

    public String email() {
        return email;
    }

    public String phone() {
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

//        if (birthDate.isAfter(LocalDate.now())) {
//            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
//        }

        this.birthDate = birthDate;
    }

    private void setEmail(String email) {

        FieldValidations.requiresValidEmail(email, VALIDATION_ERROR_EMAIL_IS_INVALID);
        this.email = email;
    }

    private void setPhone(String phone) {

        Objects.requireNonNull(phone);

        this.phone = phone;
    }

    private void setDocument(String document) {

        Objects.requireNonNull(document);

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
