package com.hankaji.icm.models.customer;

import com.hankaji.icm.lib.ID;
import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer implements GsonSerializable {
    @Id
    @Column(name = "id")
    private String cId;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "insurance_card_number")
    private InsuranceCard insuranceCard;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn (name = "holder_id")
    private Customer holder;

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private List<Customer> dependents;

    @OneToMany(mappedBy = "customer")
    private List<Claim> claims;

    public UUID getUserId(){
        return this.user.getId();
    }

    public void setHolder(Customer holder) {
        this.holder = holder;
    }

    public void setDependents(List<Customer> dependents) {
        this.dependents = dependents;
    }

    public Customer() {
        this.cId = ID.generateID(7).prefix("c-");
    }

    public Customer(String cId) {
        this.cId = ID.generateID(7).prefix("c-");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    private boolean validateId(String id) {
        // If the id is not in the format of c-numbers (c + 7 numbers), then it is invalid
        if (!id.matches("c-\\d{7}")) {
            throw new IllegalArgumentException("Invalid ID, ID must be a c-<numbers> string, where numbers contain 7 digits");
        }
        return true;
    }

    public String getHolderId() {
        return this.cId;
    }

    public Long getInsuranceCardNumber() {
        return this.insuranceCard.getCardNumber();
    }

    public List<Customer> getDependents() {
        return dependents;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cId='" + cId + '\'' +
                ", insuranceCard=" + insuranceCard +
                ", user=" + user +
                ", holder=" + holder +
                '}';
    }

    public String getId() {
        return this.cId;
    }

}
