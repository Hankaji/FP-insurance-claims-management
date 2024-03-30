package com.hankaji.icm.app.home.components;

import java.util.List;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Panel;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.services.DependentManager;

public class DependentDataPanel extends Panel {

    private DependentDataPanel() {}

    public static Border create() {
        final DependentManager depManager = DependentManager.getInstance();
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
                                }, depManager.getAll())
                                .withBorder(
                                    Borders.singleLineBevel("Dependents")
                                );

        return dataPanel;
    }
}
