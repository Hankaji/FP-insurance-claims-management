package com.hankaji.icm.app.home.components;

import java.util.List;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class DataPanelFactory {

    private DataPanelFactory() {}

    public static Border createDependent() {
        final DependentManager db = DependentManager.getInstance();
        Border dataPanel = new TableDataPanel<Dependent>(
                                List.of(
                                    String.format("%-20s", "Name"),
                                    String.format("%-12s", "ID"),
                                    String.format("Claims amount")
                                ),
                                dep -> new String[] {
                                    dep.getName(),
                                    dep.getId(),
                                    String.valueOf(dep.getClaims().size())
                                }, 
                                db)
                                .withBorder(
                                    Borders.singleLineBevel("[1] Dependents")
                                );

        return dataPanel;
    }

    public static Border createPolicyHolder() {
        final PolicyHolderManager db = PolicyHolderManager.getInstance();
        Border dataPanel = new TableDataPanel<PolicyHolder>(
                                List.of(
                                    String.format("%-20s", "Name"),
                                    String.format("%-12s", "ID"),
                                    String.format("Claims amount")
                                ),
                                dep -> new String[] {
                                    dep.getName(),
                                    dep.getId(),
                                    String.valueOf(dep.getClaims().size())
                                }, 
                                db)
                                .withBorder(
                                    Borders.singleLineBevel("[2] Policy Holders")
                                );

        return dataPanel;
    }
}
