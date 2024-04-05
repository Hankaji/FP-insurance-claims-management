package com.hankaji.icm.claim;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.lib.StringInfo;


/**
 * Represents an insurance claim.
 */
public class Claim implements StringInfo, GsonSerializable {
    private final String id;
    private final LocalDateTime claimDate;
    private final String insuredPerson;
    private final String cardNumber;
    private LocalDateTime examDate;
    private ArrayList<String> documents; // ClaimId_CardNumber_DocumentName.pdf
    private int claimAmount;
    private Status status;
    private String receiverBankingInfo; // Bank – Name – Number

    /**
     * Constructs a Claim object with the specified parameters.
     *
     * @param id                  the ID of the claim
     * @param claimDate           the date of the claim
     * @param insuredPerson       the name of the insured person
     * @param cardNumber          the card number associated with the claim
     * @param examDate            the date of the claim examination
     * @param documents           the list of documents related to the claim
     * @param claimAmount         the amount of the claim
     * @param status              the status of the claim
     * @param receiverBankingInfo the banking information of the claim receiver
     */
    public Claim(String id, LocalDateTime claimDate, String insuredPerson, String cardNumber, LocalDateTime examDate,
                 ArrayList<String> documents, int claimAmount, Status status, String receiverBankingInfo) {
        validateId(id);
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
    }

    /**
     * Validates the format of the claim ID.
     *
     * @param id the ID to validate
     * @return true if the ID is valid, false otherwise
     * @throws IllegalArgumentException if the ID is invalid
     */
    public boolean validateId(String id) {
        // If the id is not in the format of f-numbers (f + 10 numbers), then it is invalid
        if (!id.matches("f-\\d{10}")) {
            throw new IllegalArgumentException(
                    "Invalid ID, ID must be a f-<numbers> string, where numbers contain 10 digits");
        }
        return true;
    }

    /**
     * Returns the ID of the claim.
     *
     * @return the ID of the claim
     */
    public String getId() {
        validateId(id);
        return id;
    }

    /**
     * Returns the date of the claim.
     *
     * @return the date of the claim
     */
    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    /**
     * Returns the name of the insured person.
     *
     * @return the name of the insured person
     */
    public String getInsuredPerson() {
        return insuredPerson;
    }

    /**
     * Returns the card number associated with the claim.
     *
     * @return the card number associated with the claim
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Returns the date of the claim examination.
     *
     * @return the date of the claim examination
     */
    public LocalDateTime getExamDate() {
        return examDate;
    }

    /**
     * Sets the date of the claim examination.
     *
     * @param examDate the date of the claim examination
     */
    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    /**
     * Returns the list of documents related to the claim.
     *
     * @return the list of documents related to the claim
     */
    public ArrayList<String> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents related to the claim.
     *
     * @param documents the list of documents related to the claim
     */
    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    /**
     * Returns the amount of the claim.
     *
     * @return the amount of the claim
     */
    public int getClaimAmount() {
        return claimAmount;
    }

    /**
     * Sets the amount of the claim.
     *
     * @param claimAmount the amount of the claim
     */
    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    /**
     * Returns the status of the claim.
     *
     * @return the status of the claim
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the claim.
     *
     * @param status the status of the claim
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the banking information of the claim receiver.
     *
     * @return the banking information of the claim receiver
     */
    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    /**
     * Sets the banking information of the claim receiver.
     *
     * @param receiverBankingInfo the banking information of the claim receiver
     */
    public void setReceiverBankingInfo(String receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }

    /**
     * Returns a new Builder instance to build a Claim object.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Represents a builder for constructing a Claim object.
     */
    public static class Builder {
        private String id;
        private LocalDateTime claimDate;
        private String insuredPerson;
        private String cardNumber;
        private LocalDateTime examDate;
        private ArrayList<String> documents;
        private int claimAmount;
        private Status status;
        private String receiverBankingInfo;

        /**
         * Sets the ID of the claim.
         *
         * @param id the ID of the claim
         * @return the Builder instance
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the date of the claim.
         *
         * @param claimDate the date of the claim
         * @return the Builder instance
         */
        public Builder setClaimDate(LocalDateTime claimDate) {
            this.claimDate = claimDate;
            return this;
        }

        /**
         * Sets the name of the insured person.
         *
         * @param insuredPerson the name of the insured person
         * @return the Builder instance
         */
        public Builder setInsuredPerson(String insuredPerson) {
            this.insuredPerson = insuredPerson;
            return this;
        }

        /**
         * Sets the card number associated with the claim.
         *
         * @param cardNumber the card number associated with the claim
         * @return the Builder instance
         */
        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        /**
         * Sets the date of the claim examination.
         *
         * @param examDate the date of the claim examination
         * @return the Builder instance
         */
        public Builder setExamDate(LocalDateTime examDate) {
            this.examDate = examDate;
            return this;
        }

        /**
         * Sets the list of documents related to the claim.
         *
         * @param documents the list of documents related to the claim
         * @return the Builder instance
         */
        public Builder setDocuments(ArrayList<String> documents) {
            this.documents = documents;
            return this;
        }

        /**
         * Sets the amount of the claim.
         *
         * @param claimAmount the amount of the claim
         * @return the Builder instance
         */
        public Builder setClaimAmount(int claimAmount) {
            this.claimAmount = claimAmount;
            return this;
        }

        /**
         * Sets the status of the claim.
         *
         * @param status the status of the claim
         * @return the Builder instance
         */
        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the banking information of the claim receiver.
         *
         * @param receiverBankingInfo the banking information of the claim receiver
         * @return the Builder instance
         */
        public Builder setReceiverBankingInfo(String receiverBankingInfo) {
            this.receiverBankingInfo = receiverBankingInfo;
            return this;
        }

        /**
         * Builds and returns a new Claim object.
         *
         * @return a new Claim object
         */
        public Claim build() {
            return new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, status,
                    receiverBankingInfo);
        }
    }

    @Override
    public String showInfoBox() {
        return "ID: " + getId() + "\n\n" +
                "Claim Date: " + getClaimDate() + "\n\n" +
                "Insured Person: " + getInsuredPerson() + "\n\n" +
                "Card Number: " + getCardNumber() + "\n\n" +
                "Exam Date: " + getExamDate() + "\n\n" +
                "Documents: " + getDocuments().stream().reduce("", (a, b) -> a + "\n\t" + b) + "\n\n" +
                "Claim Amount: " + getClaimAmount() + "\n\n" +
                "Status: " + getStatus() + "\n\n" +
                "Receiver Banking Info: " + getReceiverBankingInfo();
    }

    /**
     * The status of the claim.
     */
    public static enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

}
