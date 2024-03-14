package com.hankaji.icm.card;

import java.util.Date;

import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.customer.PolicyHolder;

/**
 * Represents an insurance card.
 */
public class InsuranceCard {
    private String cardNumber;
    private Customer cardHolder;
    private PolicyHolder policyHolder;
    private Date expirationDate;

    /**
     * Constructs an InsuranceCard object with the specified card number, card holder, policy holder, and expiration date.
     *
     * @param cardNumber     the card number
     * @param cardHolder     the card holder
     * @param policyHolder   the policy holder
     * @param expirationDate the expiration date
     */
    public InsuranceCard(String cardNumber, Customer cardHolder, PolicyHolder policyHolder, Date expirationDate) {
        if (!validateCardNumber(cardNumber));
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyHolder = policyHolder;
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
    public Customer getCardHolder() {
        return cardHolder;
    }

    /**
     * Sets the card holder.
     *
     * @param cardHolder the card holder to set
     */
    public void setCardHolder(Customer cardHolder) {
        this.cardHolder = cardHolder;
    }

    /**
     * Returns the policy holder.
     *
     * @return the policy holder
     */
    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    /**
     * Sets the policy holder.
     *
     * @param policyHolder the policy holder to set
     */
    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }

    /**
     * Returns the expiration date.
     *
     * @return the expiration date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     *
     * @param expirationDate the expiration date to set
     */
    public void setExpirationDate(Date expirationDate) {
        if (new Date().compareTo(expirationDate) > 0) {
            throw new IllegalArgumentException("Expiration date cant be in the past");
        }
        this.expirationDate = expirationDate;
    }
}
