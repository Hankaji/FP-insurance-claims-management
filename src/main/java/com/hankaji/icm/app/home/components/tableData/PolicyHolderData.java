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
import com.hankaji.icm.app.addNewForm.PolicyHolderForm;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.services.PolicyHolderManager;

/**
 * Represents a panel that displays policy holder data in a table format.
 */
public class PolicyHolderData extends TableDataPanel<PolicyHolder> {

    /**
     * Constructs a new PolicyHolderData panel.
     * 
     * @param updateHelperText Consumer function to update the helper text.
     * @param updateInfoBox    Consumer function to update the info box.
     */
    public PolicyHolderData(Consumer<Map<String, String>> updateHelperText, Consumer<String> updateInfoBox) {
        super(List.of(
                String.format("%-12s", "ID"),
                String.format("%-20s", "Name"),
                String.format("%-12s", "Card ID"),
                String.format("%-30s", "Dependent"),
                String.format("Claims amount")),
                ph -> new String[] {
                        ph.getId(),
                        ph.getName(),
                        ph.getInsuranceCard() == null ? "N/A" : ph.getInsuranceCard().getCardNumber(),
                        String.join(", ", ph.getDependents()),
                        String.valueOf(ph.getClaims().size())
                },
                PolicyHolderManager.getInstance(),
                updateHelperText,
                updateInfoBox);
    }

    /**
     * Returns the panel with a border.
     * 
     * @return The panel with a border.
     */
    @Override
    public Border withBorder() {
        return withBorder(Borders.singleLine("[2] Policy Holder"));
    }

    /**
     * Returns the helper text to be displayed.
     * 
     * @return The helper text as a map of action descriptions and corresponding key bindings.
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
     * Opens the PolicyHolderForm window when the add key is pressed.
     */
    @Override
    protected void onAddKeyPressed() {
        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new PolicyHolderForm());
    }

    /**
     * Empty method for editing policy holder data.
     */
    @Override
    protected void onEditKeyPressed() {
    }

}
