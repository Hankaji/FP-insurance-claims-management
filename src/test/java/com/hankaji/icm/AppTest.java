package com.hankaji.icm;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.customer.Dependent;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public InsuranceCard insuranceCard = new InsuranceCard("1234567890", null, null, null);

    /**
     * Rigorous Test :-)
     */
    @Test
    public void customerID() {
        Dependent dependent = new Dependent("c-1234567", "Doe", insuranceCard, null);
        assertEquals(dependent.getId(), "c-1234567");
    }
}
