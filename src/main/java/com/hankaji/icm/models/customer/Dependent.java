package com.hankaji.icm.models.customer;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.ArrayList;

import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;

/**
 * Represents a dependent customer in the insurance claims management system.
 * A dependent is a subclass of the Customer class.
 */
public class Dependent extends Customer {

    /**
     * Constructs a new Dependent object with the specified id, name, insurance card, and claims.
     *
     * @param id             the id of the dependent
     * @param name           the name of the dependent
     * @param insuranceCard  the insurance card of the dependent
     * @param claims         the list of claims associated with the dependent
     */
    public Dependent(String id, String name, InsuranceCard insuranceCard, ArrayList<Claim> claims) {
        super(id, name, insuranceCard, claims);
    }

    /**
     * Returns a new instance of the Builder class to build a Dependent object.
     *
     * @return a new instance of the Builder class
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder class for constructing Dependent objects.
     */
    public static class Builder {
        private String id;
        private String name;
        private InsuranceCard insuranceCard;
        private ArrayList<Claim> claims;

        /**
         * Sets the id of the dependent.
         *
         * @param id the id of the dependent
         * @return the Builder object
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the dependent.
         *
         * @param name the name of the dependent
         * @return the Builder object
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the insurance card of the dependent.
         *
         * @param insuranceCard the insurance card of the dependent
         * @return the Builder object
         */
        public Builder setInsuranceCard(InsuranceCard insuranceCard) {
            this.insuranceCard = insuranceCard;
            return this;
        }

        /**
         * Sets the list of claims associated with the dependent.
         *
         * @param claims the list of claims associated with the dependent
         * @return the Builder object
         */
        public Builder setClaims(ArrayList<Claim> claims) {
            this.claims = claims;
            return this;
        }

        /**
         * Builds and returns a new Dependent object with the specified properties.
         *
         * @return a new Dependent object
         */
        public Dependent build() {
            return new Dependent(id, name, insuranceCard, claims);
        }

    }

}
