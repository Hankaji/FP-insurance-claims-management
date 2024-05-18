package com.hankaji.icm.lib;

import com.hankaji.icm.models.customer.Customer;

import java.util.Comparator;

public class CustomerIdComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        // Compare the IDs of the customers
        return c1.getId().compareTo(c2.getId());
    }
}