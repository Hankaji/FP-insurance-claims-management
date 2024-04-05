package com.hankaji.icm.customer;

/** 
 * The abstract class representing a customer in the insurance claims management system.
 * 
 * @author <Hoang Thai Phuc - s3978081> 
 * @version 1.0
 *
 * Libraries used: Lanterna, Gson, Apache Commons IO
 */
import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.lib.StringInfo;

public abstract class Customer implements StringInfo, GsonSerializable {

    private String id;
    private String name;
    private InsuranceCard insuranceCard;
    private ArrayList<Claim> claims;

    /**
     * Constructs a customer with the specified id, name, insurance card, and claims.
     * 
     * @param id the id of the customer in the format of c-numbers
     * @param name the name of the customer
     * @param insuranceCard the insurance card of the customer
     * @param claims the list of claims associated with the customer
     * @throws IllegalArgumentException if the id is invalid
     */
    protected Customer(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims) throws IllegalArgumentException {
        validateId(id);
        this.id = id;
        this.name = name;
        this.insuranceCard = insuranceCard;
        this.claims = new ArrayList<Claim>();
    }

    /**
     * Validate the id, throw error if id is in wrong format, other wise return true
     * 
     * @param id the id of the customer in the format of c-numbers
     * @return boolean, true if the id is valid, otherwise false
     */
    private boolean validateId(String id) {
        // If the id is not in the format of c-numbers (c + 7 numbers), then it is invalid
        if (!id.matches("c-\\d{7}")) {
            throw new IllegalArgumentException("Invalid ID, ID must be a c-<numbers> string, where numbers contain 7 digits");
        }
        return true;
    }

    /**
     * Returns the id of the customer.
     * 
     * @return the id of the customer
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the customer.
     * 
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the insurance card of the customer.
     * 
     * @return the insurance card of the customer
     */
    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    /**
     * Sets the insurance card of the customer.
     * 
     * @param insuranceCard the insurance card to be set
     */
    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    /**
     * Returns the list of claims associated with the customer.
     * 
     * @return the list of claims associated with the customer
     */
    public ArrayList<Claim> getClaims() {
        return claims;
    }

    /**
     * Sets the list of claims associated with the customer.
     * 
     * @param claims the list of claims to be set
     */
    public void setClaims(ArrayList<Claim> claims) {
        this.claims = claims;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((insuranceCard == null) ? 0 : insuranceCard.hashCode());
        result = prime * result + ((claims == null) ? 0 : claims.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (insuranceCard == null) {
            if (other.insuranceCard != null)
                return false;
        } else if (!insuranceCard.equals(other.insuranceCard))
            return false;
        if (claims == null) {
            if (other.claims != null)
                return false;
        } else if (!claims.equals(other.claims))
            return false;
        return true;
    }

}
