package com.hankaji.icm.models.customer;

import com.hankaji.icm.lib.ID;
import com.hankaji.icm.lib.GsonSerializable;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer implements GsonSerializable {
    @Id
    @Column(name = "id")
    private String cId;

    @Column(name = "insurance_card_number")
    private Long insuranceCardNumber;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id")
    private Customer holder;

    @OneToMany(mappedBy = "holder")
    private List<Customer> dependents;

    // Adding holderId field
    @Column(name = "holder_id", insertable = false, updatable = false)
    private String holderId;

    // Other fields and methods...

    // Getter for holderId
    public String getHolderId() {
        return holderId;
    }

    // Setter for holderId (if necessary)
    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    // Getter for id (assuming it's cId in this case)
    public String getId() {
        return cId;
    }

    // Constructor and other methods...

    public Customer(Long insuranceCardNumber, UUID userId) {
        this.cId = ID.generateID(7).prefix("c-");
        this.insuranceCardNumber = insuranceCardNumber;
        this.userId = userId;
    }

    public Customer(UUID userId) {
        this.cId = ID.generateID(7).prefix("c-");
        this.userId = userId;
    }

    public void setHolder(Customer holder) {
        this.holder = holder;
    }

    public void setDependents(List<Customer> dependents) {
        this.dependents = dependents;
    }

    public Customer() {
    }

    private boolean validateId(String id) {
        // If the id is not in the format of c-numbers (c + 7 numbers), then it is
        // invalid
        if (!id.matches("c-\\d{7}")) {
            throw new IllegalArgumentException(
                    "Invalid ID, ID must be a c-<numbers> string, where numbers contain 7 digits");
        }
        return true;
    }

    public String getcId() {
        return cId;
    }

    public Long getInsuranceCardNumber() {
        return insuranceCardNumber;
    }

    public void setInsuranceCardNumber(Long insuranceCardNumber) {
        this.insuranceCardNumber = insuranceCardNumber;
    }

    public UUID getUserId() {
        return userId;
    }

}
