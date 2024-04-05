package com.hankaji.icm.app.home.components.tableData;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.hankaji.icm.app.addNewForm.InsCardForm;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

/**
 * Represents a panel that displays insurance card data in a table format.
 * Extends the TableDataPanel class.
 */
public class InsuranceCardData extends TableDataPanel<InsuranceCard> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructs an InsuranceCardData object.
     * 
     * @param updateHelperText A consumer that updates the helper text.
     * @param updateInfoBox A consumer that updates the info box.
     */
    public InsuranceCardData(Consumer<Map<String, String>> updateHelperText, Consumer<String> updateInfoBox) {
        super(List.of(
                String.format("%-12s", "Card number"),
                String.format("%-25s", "Card holder"),
                String.format("%-30s", "Policy owner"),
                String.format("%-16s", "Expiration date")),
                card -> new String[] {
                        card.getCardNumber(),
                        card.getCardHolder().isBlank() ? "N/A" : card.getCardHolder(),
                        card.getPolicyOwner(),
                        card.getExpirationDate().format(formatter)
                },
                InsuranceCardManager.getInstance(),
                updateHelperText,
                updateInfoBox);
    }

    /**
     * Returns the panel with a border.
     * 
     * @return The panel with a border.
     */
    @Override
    public synchronized Border withBorder() {
        return withBorder(Borders.singleLine("[3] Insurance cards"));
    }

    /**
     * Returns a map of helper text.
     * 
     * @return A map of helper text.
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
     * Opens the InsCardForm window when the add key is pressed.
     */
    @Override
    protected void onAddKeyPressed() {
        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new InsCardForm());
    }

    /**
     * Does nothing when the edit key is pressed.
     */
    @Override
    protected void onEditKeyPressed() {
    }

    /**
     * Deletes the selected insurance card and removes it from the associated customer.
     */
    @Override
    protected void onDeleteKeyPressed() {
        String cardHolder = table.getTableModel().getRow(table.getSelectedRow()).get(1);
        super.onDeleteKeyPressed();

        List<Customer> customers = new ArrayList<>(DependentManager.getInstance().getAll());
        customers.addAll(PolicyHolderManager.getInstance().getAll());

        final String nameOnly = cardHolder.replaceAll("\\(.*?\\)", "").trim();
        try {
            Customer holderObj = customers.stream()
                    .filter(c -> c.getName().equals(nameOnly))
                    .findFirst()
                    .get();

            holderObj.setInsuranceCard(null);
        } catch (Exception e) {}

    }
}
