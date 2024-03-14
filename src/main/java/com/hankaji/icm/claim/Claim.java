package com.hankaji.icm.claim;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.hankaji.icm.customer.Customer;

public class Claim {
    private String id;
    private LocalDateTime claimDate;
    private Customer insuredPerson;
    private String cardNumber;
    private LocalDateTime examDate;
    private ArrayList<String> documents;
    private int claimAmount;
    private ClaimStatus status;
    private String receiverBankingInfo;

    public Claim(String id, LocalDateTime claimDate, Customer insuredPerson, String cardNumber, LocalDateTime examDate,
            ArrayList<String> documents, int claimAmount, ClaimStatus status, String receiverBankingInfo) {
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
    
}
