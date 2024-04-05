package com.hankaji.icm.app.home.components.tableData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.app.addNewForm.DependentForm;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.StringInfo;
import com.hankaji.icm.services.DependentManager;

public class DependentData extends TableDataPanel<Dependent> {

    public DependentData(Consumer<Map<String, String>> updateHelperText, Consumer<String> updateInfoBox) {
        super(
            List.of(
                String.format("%-12s", "ID"),
                String.format("%-20s", "Name"),
                String.format("%-12s", "Card ID"),
                String.format("Claims amount")
            ),
            dep -> new String[] {
                dep.getId(),
                dep.getName(),
                dep.getInsuranceCard() == null ? "N/A" : dep.getInsuranceCard().getCardNumber(),
                String.valueOf(dep.getClaims().size())
            }, 
            DependentManager.getInstance(),
            updateHelperText,
            updateInfoBox);

    }

    @Override
    public Border withBorder() {
        return withBorder(Borders.singleLine("[1] Dependent"));
    }

    @Override
    protected Map<String, String> useHelperText() {
        Map<String, String> helperText = new LinkedHashMap<>();
        helperText.put("Add", "a");
        // helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        // helperText.put("DeleteAll", "<ctrl-D>");

        return helperText;
    }

    @Override
    protected void onAddKeyPressed() {
        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new DependentForm());
    }

    @Override
    protected void onEditKeyPressed() {}

}
