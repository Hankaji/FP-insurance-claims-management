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
    @OneToMany(mappedBy = "policyOwner")
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

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(Double annualRate) {
        this.annualRate = annualRate;
    }

    public List<InsuranceCard> getCards() {
        return cards;
    }

    public void setCards(List<InsuranceCard> cards) {
        this.cards = cards;
    }

    public User getUser() {
        return user;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    
}
