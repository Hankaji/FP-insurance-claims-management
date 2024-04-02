package com.hankaji.icm.app.home.components.tableData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.app.addNewForm.AddDependent;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.services.DependentManager;

public class DependentData extends TableDataPanel<Dependent> {

    public DependentData(Consumer<Map<String, String>> updateHelperText, Consumer<String> updateInfoBox) {
        super(
            List.of(
                String.format("%-20s", "Name"),
                String.format("%-12s", "ID"),
                String.format("%-12s", "Card ID"),
                String.format("Claims amount")
            ),
            dep -> new String[] {
                dep.getName(),
                dep.getId(),
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
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 'a':
                ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new AddDependent());
                update();
                return true;
            case 'd':
                {
                    String id = table.getTableModel().getRow(table.getSelectedRow()).get(1);
                    table.getTableModel().removeRow(table.getSelectedRow());
                    DependentManager.getInstance().delete(id.trim());
                }
                return true;
            case null:
                break;
            default:
                break;
        }
        return super.handleInput(key);
    }

}
