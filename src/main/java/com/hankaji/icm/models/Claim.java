package com.hankaji.icm.models;
/**
 * @author <Hoang Thai Phuc - s3978081>
 * @version 1.0
 *
 * Libraries used: Lanterna, Gson, Apache Commons IO
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.models.customer.Customer;
import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an insurance claim.
 */
@Entity
@Table(name = "claims")
public class Claim implements GsonSerializable {
    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "claim_date")
    private LocalDateTime claimDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insured_person_id")
    private Customer customer;
    @Column(name = "exam_date")
    private LocalDateTime examDate;
    @Column(name = "document")
    private String documents; // ClaimId_CardNumber_DocumentName.pdf
    @Column(name = "claim_amount")
    private Double claimAmount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "receiver_banking_info")
    private String receiverBankingInfo; // Bank – Name – Number
    @Column(name = "card_number")
    private Long insuranceCardNumber;

    public Claim() {
    }

    public Claim(String id, String documents, Double claimAmount, Status status, String receiverBankingInfo) {
        this.id = id;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public Claim(String documents, Double claimAmount, Status status, String receiverBankingInfo) {
        this.id = ID.generateID(10).prefix("f-");
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public Claim(String title, String description, Customer customer, LocalDateTime claimDate, LocalDateTime examDate, String documents, Double claimAmount, Status status, String receiverBankingInfo, Long insuranceCardNumber) {
        this.id = ID.generateID(10).prefix("f-");
        this.title = title;
        this.description = description;
        this.setCustomer(customer);
        this.claimDate = claimDate;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverBankingInfo = receiverBankingInfo;
        this.insuranceCardNumber = insuranceCardNumber;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
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
    public String getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents related to the claim.
     *
     * @param documents the list of documents related to the claim
     */
    public void setDocuments(String documents) {
        this.documents = documents;
    }

    /**
     * Returns the amount of the claim.
     *
     * @return the amount of the claim
     */
    public Double getClaimAmount() {
        return claimAmount;
    }

    /**
     * Sets the amount of the claim.
     *
     * @param claimAmount the amount of the claim
     */
    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Status getStatus() {
        return status;
    }

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

    public String getReceiver_banking_info() {
        return this.receiverBankingInfo;
    }

    public Double getClaim_amount() {
        return this.claimAmount;
    }

    public Long getCard_number() {
        return this.customer.getInsuranceCardNumber();
    }

    public String getInsured_person_id() {
        return this.customer.getHolderId();
    }

    public Long getInsuranceCardNumber() {
        return insuranceCardNumber;
    }


    public static enum Status {
        NEW,
        PROCESSING,
        DONE
    }
}