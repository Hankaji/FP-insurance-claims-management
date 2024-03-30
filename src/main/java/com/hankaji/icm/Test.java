package com.hankaji.icm;

import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.services.DependentManager;

public class Test {
    public static void main(String[] args) {
        DependentManager depManager = DependentManager.getInstance();

        // Dependent dep1 = Dependent.builder()
        //     .setId("c-3977773")
        //     .setName("Hoang Minh")
        //     .build();

        // Dependent dep2 = Dependent.builder()
        //     .setId("c-3912414")
        //     .setName("Thai Minh")
        //     .build();

        // depManager.add(dep1);
        // depManager.add(dep2);

        System.out.println(depManager.getAll());
    }
}
