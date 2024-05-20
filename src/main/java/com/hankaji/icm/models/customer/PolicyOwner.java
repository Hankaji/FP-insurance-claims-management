package com.hankaji.icm.models.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.providers.Provider;
import jakarta.persistence.*;

@Entity
@Table(name = "policy_owners")
public class PolicyOwner {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "annual_rate")
    private Double annualRate;
    @OneToMany(mappedBy = "policyOwnerId")
    private List<InsuranceCard> cards;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "card_provider_id")
    private Provider provider;

    public PolicyOwner(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public PolicyOwner() {
    }

    public PolicyOwner(UUID id, String name, Double annualRate) {
        this.id = id;
        this.name = name;
        this.annualRate = annualRate;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }
}
