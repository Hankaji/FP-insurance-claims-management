package com.hankaji.icm.customer;

import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;

public class PolicyHolder extends Customer {

    private ArrayList<Dependent> dependents;

    public PolicyHolder(String id, String name, InsuranceCard insuranceCard, ArrayList<Dependent> dependents) {
        super(id, name, insuranceCard);
        this.dependents = dependents;
    }

    public ArrayList<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;
    }
    
}
