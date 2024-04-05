package com.hankaji.icm.app.home.components.tableData;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.hankaji.icm.app.addNewForm.DependentForm;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.services.DependentManager;

/**
 * Represents a panel that displays table data for dependents.
 * Extends the TableDataPanel class.
 * 
 * @param <Dependent> the type of dependent data to be displayed in the table
 */
public class DependentData extends TableDataPanel<Dependent> {

    /**
     * Constructs a new DependentData panel with the given update helper text and update info box consumers.
     *
     * @param updateHelperText A consumer that updates the helper text.
     * @param updateInfoBox    A consumer that updates the info box.
     */
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

    /**
     * Returns the border for the DependentData panel.
     *
     * @return The border for the DependentData panel.
     */
    @Override
    public Border withBorder() {
        return withBorder(Borders.singleLine("[1] Dependent"));
    }

    /**
     * Returns a map of helper text for various actions in the DependentData panel.
     *
     * @return A map of helper text for various actions.
     */
    @Override
    protected Map<String, String> useHelperText() {
        Map<String, String> helperText = new LinkedHashMap<>();
        helperText.put("Add", "a");
        // helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        // helperText.put("DeleteAll", "<ctrl-D>");

        return helperText;
    }

    /**
     * Called when the add key is pressed in the DependentData panel.
     * Opens a new DependentForm window.
     */
    @Override
    protected void onAddKeyPressed() {
        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new DependentForm());
    }

    /**
     * Called when the edit key is pressed in the DependentData panel.
     * Currently, this method is empty and does not perform any action.
     */
    @Override
    protected void onEditKeyPressed() {}

}
