package com.hankaji.icm.models;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.time.LocalDateTime;
import java.util.UUID;

import com.hankaji.icm.lib.GsonSerializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an insurance card.
 */
@Entity
@Table(name = "insurance_cards")
public class InsuranceCard implements GsonSerializable {
    @Id
    @Column(name = "card_number")
    private Long cardNumber;
    @Column(name = "card_holder_id")
    private String cardHolderId;
    @Column(name = "policy_owner_id")
    private UUID policyOwnerId;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public InsuranceCard(Long cardNumber) {
        this.cardNumber = cardNumber;
        this.expirationDate = LocalDateTime.now().plusYears(5);
    }

    /**
     * Constructs an InsuranceCard object with the specified card number, card
     * holder, policy holder, and expiration date.
     *
     * @param cardNumber     the card number
     * @param cardHolder     the card holder
     * @param policyHolder   the policy holder
     * @param expirationDate the expiration date
     */
    public InsuranceCard(Long cardNumber, String cardHolder, UUID policyHolder, LocalDateTime expirationDate) {
        if (!validateCardNumber(cardNumber.toString()));
        this.cardNumber = cardNumber;
        this.cardHolderId = cardHolder;
        this.policyOwnerId = policyHolder;
        this.expirationDate = expirationDate;
    }

    /**
     * Validates the card number.
     *
     * @param cardNumber the card number to validate
     * @return true if the card number is valid, false otherwise
     * @throws IllegalArgumentException if the card number is in the wrong format
     */
    private boolean validateCardNumber(String cardNumber) {
        // If the card number is not in the format of 10 digits, then it is invalid
        if (!cardNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid card number, number must be a 10-digit string");
        }
        return true;
    }

    /**
     * Returns the card number.
     *
     * @return the card number
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the card holder.
     *
     * @return the card holder
     */
    public String getCardHolderId() {
        return cardHolderId;
    }

    /**
     * Sets the card holder.
     *
     * @param cardHolder the card holder to set
     */
    public void setCardHolderId(String cardHolder) {
        this.cardHolderId = cardHolder;
    }

    /**
     * Returns the policy holder.
     *
     * @return the policy holder
     */
    public UUID getPolicyOwnerId() {
        return policyOwnerId;
    }

    /**
     * Sets the policy holder.
     *
     * @param policyHolder the policy holder to set
     */
    public void setPolicyHolder(UUID policyHolder) {
        this.policyOwnerId = policyHolder;
    }

    /**
     * Returns the expiration date.
     *
     * @return the expiration date
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     *
     * @param expirationDate the expiration date to set
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration date cant be in the past");
        }
        this.expirationDate = expirationDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long cardNumber;
        private String cardHolder;
        private UUID policyOwner;
        private LocalDateTime expirationDate;

        public Builder setCardNumber(Long cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder setPolicyOwner(UUID policyOwner) {
            this.policyOwner = policyOwner;
            return this;
        }

        public Builder setExpirationDate(LocalDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public InsuranceCard build() {
            return new InsuranceCard(cardNumber, cardHolder, policyOwner, expirationDate);
        }
    }

}
