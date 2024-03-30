package com.hankaji.icm.app.home.components;

import java.util.List;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Panel;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.services.PolicyHolderManager;

public class PolicyHolderDataPanel extends Panel {

    private PolicyHolderDataPanel() {}

    public static Border create() {
        final PolicyHolderManager depManager = PolicyHolderManager.getInstance();
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
                                }, depManager.getAll())
                                .withBorder(
                                    Borders.singleLineBevel("Policy Holders")
                                );

        return dataPanel;
    }
}
