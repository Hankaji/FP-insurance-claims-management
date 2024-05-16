package com.hankaji.icm.models.customer;

import java.util.ArrayList;
import java.util.UUID;

import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;
import jakarta.persistence.*;

@Entity
@Table(name = "policy_owners")
public class PolicyOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "annual_rate")
    private Double annualRate;

    public PolicyOwner() {
    }

    public PolicyOwner(UUID id, String name, Double annualRate) {
        this.id = id;
        this.name = name;
        this.annualRate = annualRate;
    }
}
