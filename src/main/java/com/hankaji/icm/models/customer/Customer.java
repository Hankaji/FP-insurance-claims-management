package com.hankaji.icm.models.customer;

/** 
 * The abstract class representing a customer in the insurance claims management system.
 * 
 * @author <Hoang Thai Phuc - s3978081> 
 * @version 1.0
 *
 * Libraries used: Lanterna, Gson, Apache Commons IO
 */
import java.util.List;
import java.util.UUID;

import com.hankaji.icm.lib.ID;
import com.hankaji.icm.lib.GsonSerializable;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer implements GsonSerializable {
    @Id
    @Column(name="id")
    private String cId;
    @Column(name = "insurance_card_number")
    private Long insuranceCardNumber;
    @Column(name = "user_id")
    private UUID userId;
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn (name = "holder_id")
    private Customer holder;

    @OneToMany(mappedBy = "holder")
    private List<Customer> dependents;

    public Customer(Long insuranceCardNumber, UUID userId) {
        this.cId = ID.generateID(7).prefix("c-");
        this.insuranceCardNumber = insuranceCardNumber;
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
        // If the id is not in the format of c-numbers (c + 7 numbers), then it is invalid
        if (!id.matches("c-\\d{7}")) {
            throw new IllegalArgumentException("Invalid ID, ID must be a c-<numbers> string, where numbers contain 7 digits");
        }
        return true;
    }
}
