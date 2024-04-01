package com.hankaji.icm.app.home.components;

import java.util.List;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.services.DependentManager;

public class DependentData extends TableDataPanel<Dependent> {

    public DependentData() {
        //Collection<String> tableTitles, Function<Dependent, String[]> rowMapper, DataManager<Dependent> db
        super(
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
            DependentManager.getInstance());

    }

    @Override
    public Border withBorder() {
        return withBorder(Borders.singleLine("[1] Dependent"));
    }

}
