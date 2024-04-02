package com.hankaji.icm.customer;

import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;

public class PolicyHolder extends Customer {

    private ArrayList<String> dependents;

    public PolicyHolder(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims, ArrayList<String> dependents) {
        super(id, name, insuranceCard, claims);
        this.dependents = dependents;
    }

    public ArrayList<String> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<String> dependents) {
        this.dependents = dependents;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private InsuranceCard insuranceCard;
        private ArrayList<Claim> claims;
        private ArrayList<String> dependents;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setInsuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return this;
        }

        public Builder setClaims(ArrayList<Claim> claims) {
            this.claims = claims;
            return this;
        }

        public Builder setDependents(ArrayList<String> dependents) {
            this.dependents = dependents;
            return this;
        }

        public PolicyHolder build() {
            return new PolicyHolder(id, name, insuranceCard, claims, dependents);
        }
        
    }

    @Override
    public String showInfoBox() {
        return "Name: " + getName() + "\n" + 
        "ID: " + getId() + "\n" + 
        "Insurance Card: " + getInsuranceCard().getCardNumber() +
        "Dependents: " + getDependents() +
        getClaims().stream().map(Claim::getId).reduce("", (a, b) -> a + "\n" + b);
    }
    
}
