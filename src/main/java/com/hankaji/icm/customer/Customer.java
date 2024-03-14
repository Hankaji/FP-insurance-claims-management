package com.hankaji.icm.customer;

import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;

public abstract class Customer {
    
    private String id;
    private String name;
    private InsuranceCard insuranceCard;
    private ArrayList<String> claims; // TODO: TBC: Claim list

    public Customer(String id, String name, InsuranceCard insuranceCard) {
        if (!validateId(id));
        this.id = id;
        this.name = name;
        this.insuranceCard = insuranceCard;
        this.claims = new ArrayList<String>();
    }

    /**
     * Validate the id, throw error if id is in wrong format, other wise return true
     * 
     * @param id the id of the customer in the format of c-numbers
     * @return boolean, true if the id is valid, otherwise false
     */
    private boolean validateId(String id) {
        // If the id is not in the format of c-numbers (c + 7 numbers), then it is invalid
        if (!id.matches("c-\\d{7}")) {
            throw new IllegalArgumentException("Invalid ID, ID must be a c-<numbers> string, where numbers contain 7 digits");
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (!validateId(id)) return;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public ArrayList<String> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<String> claims) {
        this.claims = claims;
    }

}
