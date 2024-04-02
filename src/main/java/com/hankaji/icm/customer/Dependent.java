package com.hankaji.icm.customer;

import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;

public class Dependent extends Customer {

    public Dependent(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims) {
        super(id, name, insuranceCard, claims);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private InsuranceCard insuranceCard;
        private ArrayList<Claim> claims;

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

        public Dependent build() {
            return new Dependent(id, name, insuranceCard, claims);
        }

    }

    @Override
    public String showInfoBox() {
        return "Name: " + getName() + "\n" +
                "ID: " + getId() + "\n" +
                "Insurance Card: " + getInsuranceCard().getCardNumber() +
                getClaims().stream().map(Claim::getId).reduce("", (a, b) -> a + "\n" + b);
    }

}
