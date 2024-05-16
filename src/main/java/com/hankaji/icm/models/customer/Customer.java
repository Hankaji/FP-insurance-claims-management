package com.hankaji.icm.models.customer;

/** 
 * The abstract class representing a customer in the insurance claims management system.
 * 
 * @author <Hoang Thai Phuc - s3978081> 
 * @version 1.0
 *
 * Libraries used: Lanterna, Gson, Apache Commons IO
 */
import java.util.UUID;

import com.hankaji.icm.lib.ID;
import com.hankaji.icm.lib.GsonSerializable;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer implements GsonSerializable {
    @Id
    @Column(name="id")
    protected String cId;
    @Column(name = "insurance_card_number")
    protected Long insuranceCardNumber;
    @Column(name = "user_id")
    protected UUID userId;
    @Column(name = "holder_id")
    private UUID dependentListId;

    public Customer(Long insuranceCardNumber, UUID userId, UUID dependentListId) {
        this.cId = ID.generateID(7).prefix("c-");
        this.insuranceCardNumber = insuranceCardNumber;
        this.userId = userId;
        this.dependentListId = dependentListId;
    }

    public Customer(Long insuranceCardNumber, UUID userId) {
        this.cId = ID.generateID(7).prefix("c-");
        this.insuranceCardNumber = insuranceCardNumber;
        this.userId = userId;
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

    public UUID getDependentListId() {
        return dependentListId;
    }

    public void setDependentListId(UUID dependentListId) {
        this.dependentListId = dependentListId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long insuranceCardNumber;
        private UUID userId;
        private UUID dependentListId;

        public Builder insuranceCardNumber(Long insuranceCardNumber) {
            this.insuranceCardNumber = insuranceCardNumber;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder dependentListId(UUID dependentListId) {
            this.dependentListId = dependentListId;
            return this;
        }

        public Customer build() {
            return new Customer(insuranceCardNumber, userId, dependentListId);
        }
    }
}
