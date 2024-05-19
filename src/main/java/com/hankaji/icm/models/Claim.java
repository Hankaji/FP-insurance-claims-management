package com.hankaji.icm.models;

import com.hankaji.icm.lib.GsonSerializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "claims")
public class Claim implements GsonSerializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "insured_person_id")
    private String insured_person_id;

    @Column(name = "card_number")
    private BigDecimal card_number;

    @Column(name = "exam_date")
    private Timestamp exam_date;

    @Column(name = "claim_amount")
    private BigDecimal claim_amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "receiver_banking_info")
    private String receiver_banking_info;

    @Column(name = "claim_date")
    private Timestamp claim_date;

    @Column(name = "document")
    private String document;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsured_person_id() {
        return insured_person_id;
    }

    public void setInsured_person_id(String insured_person_id) {
        this.insured_person_id = insured_person_id;
    }

    public BigDecimal getCard_number() {
        return card_number;
    }

    public void setCard_number(BigDecimal card_number) {
        this.card_number = card_number;
    }

    public Timestamp getExam_date() {
        return exam_date;
    }

    public void setExam_date(Timestamp exam_date) {
        this.exam_date = exam_date;
    }

    public BigDecimal getClaim_amount() {
        return claim_amount;
    }

    public void setClaim_amount(BigDecimal claim_amount) {
        this.claim_amount = claim_amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReceiver_banking_info() {
        return receiver_banking_info;
    }

    public void setReceiver_banking_info(String receiver_banking_info) {
        this.receiver_banking_info = receiver_banking_info;
    }

    public Timestamp getClaim_date() {
        return claim_date;
    }

    public void setClaim_date(Timestamp claim_date) {
        this.claim_date = claim_date;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public static enum Status {
        NEW, PROCESSING, DONE
    }
}