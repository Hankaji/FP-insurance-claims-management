package com.hankaji.icm.app.home.components.tableData;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.app.addNewForm.AddNewCard;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.services.InsuranceCardManager;

public class InsuranceCardData extends TableDataPanel<InsuranceCard> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InsuranceCardData(Consumer<Map<String, String>> updateHelperText) {
        super(List.of(
                String.format("%-12s", "Card number"),
                String.format("%-25s", "Card holder"),
                String.format("%-20s", "Policy owner"),
                String.format("%-20s", "Expiration date")),
                card -> new String[] {
                        card.getCardNumber(),
                        card.getCardHolder(),
                        card.getPolicyOwner(),
                        card.getExpirationDate().format(formatter)
                },
                InsuranceCardManager.getInstance(),
                updateHelperText);
    }

    @Override
    public synchronized Border withBorder() {
        return withBorder(Borders.singleLine("[3] Insurance cards"));
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
                ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new AddNewCard());
                update();
                return true;
            case 'd':
                {
                    String cardNumber = table.getTableModel().getRow(table.getSelectedRow()).get(0);
                    table.getTableModel().removeRow(table.getSelectedRow());
                    InsuranceCardManager.getInstance().delete(cardNumber.trim());
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
