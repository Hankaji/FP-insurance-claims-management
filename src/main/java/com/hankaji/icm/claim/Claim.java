package com.hankaji.icm.claim;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.hankaji.icm.lib.StringInfo;

public class Claim implements StringInfo {
    private final String id;
    private final LocalDateTime claimDate;
    private final String insuredPerson;
    private final String cardNumber;
    private LocalDateTime examDate;
    private ArrayList<String> documents; // ClaimId_CardNumber_DocumentName.pdf
    private int claimAmount;
    private ClaimStatus status;
    private String receiverBankingInfo; // Bank – Name – Number

    public Claim(String id, LocalDateTime claimDate, String insuredPerson, String cardNumber, LocalDateTime examDate,
            ArrayList<String> documents, int claimAmount, ClaimStatus status, String receiverBankingInfo) {
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

    public boolean validateId(String id) {
        // If the id is not in the format of f-numbers (f + 10 numbers), then it is invalid
        if (!id.matches("f-\\d{10}")) {
            throw new IllegalArgumentException("Invalid ID, ID must be a f-<numbers> string, where numbers contain 10 digits");
        }
        return true;
    }

    public String getId() {
        validateId(id);
        return id;
    }

    public LocalDateTime getClaimDate() {
        return claimDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDateTime getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDateTime examDate) {
        this.examDate = examDate;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    public void setReceiverBankingInfo(String receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private LocalDateTime claimDate;
        private String insuredPerson;
        private String cardNumber;
        private LocalDateTime examDate;
        private ArrayList<String> documents;
        private int claimAmount;
        private ClaimStatus status;
        private String receiverBankingInfo;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setClaimDate(LocalDateTime claimDate) {
            this.claimDate = claimDate;
            return this;
        }

        public Builder setInsuredPerson(String insuredPerson) {
            this.insuredPerson = insuredPerson;
            return this;
        }

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setExamDate(LocalDateTime examDate) {
            this.examDate = examDate;
            return this;
        }

        public Builder setDocuments(ArrayList<String> documents) {
            this.documents = documents;
            return this;
        }

        public Builder setClaimAmount(int claimAmount) {
            this.claimAmount = claimAmount;
            return this;
        }

        public Builder setStatus(ClaimStatus status) {
            this.status = status;
            return this;
        }

        public Builder setReceiverBankingInfo(String receiverBankingInfo) {
            this.receiverBankingInfo = receiverBankingInfo;
            return this;
        }

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

}
