package com.hankaji.icm.customer;

import com.hankaji.icm.card.InsuranceCard;

public class Dependent extends Customer {

    public Dependent(String id, String name, InsuranceCard insuranceCard) {
        super(id, name, insuranceCard);
    }

    public Dependent(String id, String name) {
        super(id, name);
    }
    
}
