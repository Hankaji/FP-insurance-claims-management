package com.hankaji.icm.app.home.components;

import java.util.List;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.services.PolicyHolderManager;

public class PolicyHolderData extends TableDataPanel<PolicyHolder> {

    public PolicyHolderData() {
        super(List.of(
                String.format("%-20s", "Name"),
                String.format("%-12s", "ID"),
                String.format("%-30s", "Dependent"),
                String.format("Claims amount")),
                ph -> new String[] {
                        ph.getName(),
                        ph.getId(),
                        String.join(", ", ph.getDependents()),
                        String.valueOf(ph.getClaims().size())
                },
                PolicyHolderManager.getInstance());
    }

    @Override
    public Border withBorder() {
        return withBorder(Borders.singleLine("[2] Policy Holder"));
    }

}
