package com.hankaji.icm;

import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.customer.DependentManager;
import com.hankaji.icm.customer.Dependent;

public class Test {
    public static void main(String[] args) {
        DependentManager depManager = DependentManager.getInstance();

        // Customer customer = new Dependent("c-3978081", "Thai Phuc");

        // depManager.add((Dependent)customer);

        System.out.println(depManager.getAll().iterator().next().getName());
    }
}
