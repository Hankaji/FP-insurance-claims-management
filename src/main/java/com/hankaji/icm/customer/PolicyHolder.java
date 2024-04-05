package com.hankaji.icm.customer;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.ArrayList;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;


/**
 * Represents a policy holder, who is a customer with dependents and insurance claims.
 */
public class PolicyHolder extends Customer {

    private ArrayList<String> dependents;

    /**
     * Constructs a new PolicyHolder object with the specified parameters.
     *
     * @param id            the ID of the policy holder
     * @param name          the name of the policy holder
     * @param insuranceCard the insurance card of the policy holder
     * @param claims        the list of insurance claims of the policy holder
     * @param dependents    the list of dependents of the policy holder
     */
    public PolicyHolder(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims, ArrayList<String> dependents) {
        super(id, name, insuranceCard, claims);
        this.dependents = dependents;
    }

    /**
     * Returns the list of dependents of the policy holder.
     *
     * @return the list of dependents
     */
    public ArrayList<String> getDependents() {
        return dependents;
    }

    /**
     * Sets the list of dependents of the policy holder.
     *
     * @param dependents the list of dependents to set
     */
    public void setDependents(ArrayList<String> dependents) {
        this.dependents = dependents;
    }

    /**
     * Returns a new instance of the Builder class to build a PolicyHolder object.
     *
     * @return a new instance of the Builder class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Represents a builder class to build a PolicyHolder object.
     */
    public static class Builder {
        private String id;
        private String name;
        private InsuranceCard insuranceCard;
        private ArrayList<Claim> claims;
        private ArrayList<String> dependents;

        /**
         * Sets the ID of the policy holder.
         *
         * @param id the ID to set
         * @return the Builder object
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the policy holder.
         *
         * @param name the name to set
         * @return the Builder object
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the insurance card of the policy holder.
         *
         * @param insuranceCard the insurance card to set
         * @return the Builder object
         */
        public Builder setInsuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return this;
        }

        /**
         * Sets the list of insurance claims of the policy holder.
         *
         * @param claims the list of insurance claims to set
         * @return the Builder object
         */
        public Builder setClaims(ArrayList<Claim> claims) {
            this.claims = claims;
            return this;
        }

        /**
         * Sets the list of dependents of the policy holder.
         *
         * @param dependents the list of dependents to set
         * @return the Builder object
         */
        public Builder setDependents(ArrayList<String> dependents) {
            this.dependents = dependents;
            return this;
        }

        /**
         * Builds and returns a new PolicyHolder object with the set properties.
         *
         * @return a new PolicyHolder object
         */
        public PolicyHolder build() {
            return new PolicyHolder(id, name, insuranceCard, claims, dependents);
        }

    }

    /**
     * Returns a string representation of the policy holder's information.
     *
     * @return a string representation of the policy holder's information
     */
    @Override
    public String showInfoBox() {
        return "Name: " + getName() + "\n" +
                "ID: " + getId() + "\n" +
                "Insurance Card: " + getInsuranceCard() + "\n" +
                "Dependents: " + getDependents() +
                getClaims().stream().map(Claim::getId).reduce("", (a, b) -> a + "\n" + b);
    }

}
