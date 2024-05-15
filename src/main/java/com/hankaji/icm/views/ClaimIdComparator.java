package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;

import java.util.Comparator;

public class ClaimIdComparator implements Comparator<Claim> {
    @Override
    public int compare(Claim claim1, Claim claim2) {
        // Compare claim IDs
        return claim1.getId().compareTo(claim2.getId());
    }
}