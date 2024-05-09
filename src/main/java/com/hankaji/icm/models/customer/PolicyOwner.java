package com.hankaji.icm.models.customer;

import java.util.ArrayList;

import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;

public class PolicyOwner extends Customer {

    protected PolicyOwner(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims)
            throws IllegalArgumentException {
        super(id, name, insuranceCard, claims);
        
    }
    
}
