package com.hankaji.icm.lib;

import com.hankaji.icm.models.Claim;

import java.util.Comparator;

public class ClaimIdComparator implements Comparator<Claim> {
    @Override
    public int compare(Claim claim1, Claim claim2) {
        // Compare claim IDs
        return claim1.getId().compareTo(claim2.getId());
    }
}