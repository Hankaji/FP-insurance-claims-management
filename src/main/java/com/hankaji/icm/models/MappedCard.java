package com.hankaji.icm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_cards")
public class MappedCard {
    @Id
    @Column(name = "card_number")
    private Long cardNumber;
    @Column(name = "card_holder_id")
    private String cardHolderId;
    @Column(name = "policy_owner_id")
    private String policyOwnerId;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    public MappedCard(Long cardNumber, String cardHolderId, String policyOwnerId, LocalDateTime expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolderId = cardHolderId;
        this.policyOwnerId = policyOwnerId;
        this.expirationDate = expirationDate;
    }

    public MappedCard(Long cardNumber, LocalDateTime expirationDate) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public MappedCard() {
    }

    @Override
    public String toString() {
        return "MappedCard{" +
                "cardNumber=" + cardNumber +
                ", cardHolderId='" + cardHolderId + '\'' +
                ", policyOwnerId='" + policyOwnerId + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
