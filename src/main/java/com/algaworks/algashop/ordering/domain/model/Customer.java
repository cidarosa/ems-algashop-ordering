package com.algaworks.algashop.ordering.domain.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Customer {

    private UUID id;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private Integer loyaltyPoints;

    public Customer(UUID id, String fullName, LocalDate birthDate,
                    String email, String phone,
                    String document,
                    Boolean promotionNotificationAllowed,
                    OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt,
                    Integer loyaltyPoints) {
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

    public Customer(UUID id, String fullName, LocalDate birthDate,
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
        this.setLoyaltyPoints(0);
    }

    public void addLoyaltPoint(Integer points) {

    }

    public void archive() {

    }

    public void enablePromotionNotifications() {
        setPromotionNotificationAllowed(true);
    }

    public void disablePromotionNotifications() {
        setPromotionNotificationAllowed(false);
    }

    public void changeName(String fullName) {
        setFullName(fullName);
    }

    public void changeEmail(String email) {
        setEmail(email);
    }

    public void changePhone(String phone) {
        setPhone(phone);
    }

    public String document() {
        return document;
    }

    // metodos getters similar aos records
    public UUID id() {
        return id;
    }

    public String fullName() {
        return fullName;
    }

    public LocalDate birthDate() {
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

    public Integer loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setId(UUID id) {

        Objects.requireNonNull(id);
        this.id = id;
    }

    private void setFullName(String fullName) {
        Objects.requireNonNull(fullName);

        if (fullName.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.fullName = fullName;
    }

    private void setBirthDate(LocalDate birthDate) {

        if (birthDate == null) {
            this.birthDate = null;
            return;
        }

        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException();
        }

        this.birthDate = birthDate;
    }

    private void setEmail(String email) {

        Objects.requireNonNull(email);

        if (email.isBlank()) {
            throw new IllegalArgumentException();
        }

        // usa a biblioteca commons-validator
        if (!EmailValidator.getInstance().isValid(email)) {

            throw new IllegalArgumentException();
        }
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

    private void setLoyaltyPoints(Integer loyaltyPoints) {

        Objects.requireNonNull(loyaltyPoints);

        this.loyaltyPoints = loyaltyPoints;
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
