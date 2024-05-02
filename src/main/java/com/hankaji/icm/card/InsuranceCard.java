package com.hankaji.icm.card;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.time.LocalDateTime;
import com.hankaji.icm.lib.GsonSerializable;

/**
 * Represents an insurance card.
 */
public class InsuranceCard implements GsonSerializable {
    private String cardNumber;
    private String cardHolder;
    private String policyOwner;
    private LocalDateTime expirationDate;

    /**
     * Constructs an InsuranceCard object with the specified card number, card
     * holder, policy holder, and expiration date.
     *
     * @param cardNumber     the card number
     * @param cardHolder     the card holder
     * @param policyHolder   the policy holder
     * @param expirationDate the expiration date
     */
    public InsuranceCard(String cardNumber, String cardHolder, String policyHolder, LocalDateTime expirationDate) {
        if (!validateCardNumber(cardNumber))
            ;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = policyHolder;
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
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the card number.
     *
     * @param cardNumber the card number to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Returns the card holder.
     *
     * @return the card holder
     */
    public String getCardHolder() {
        return cardHolder;
    }

    /**
     * Sets the card holder.
     *
     * @param cardHolder the card holder to set
     */
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    /**
     * Returns the policy holder.
     *
     * @return the policy holder
     */
    public String getPolicyOwner() {
        return policyOwner;
    }

    /**
     * Sets the policy holder.
     *
     * @param policyHolder the policy holder to set
     */
    public void setPolicyHolder(String policyHolder) {
        this.policyOwner = policyHolder;
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
        private String cardNumber;
        private String cardHolder;
        private String policyOwner;
        private LocalDateTime expirationDate;

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setCardHolder(String cardHolder) {
            this.cardHolder = cardHolder;
            return this;
        }

        public Builder setPolicyOwner(String policyOwner) {
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
