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
import com.hankaji.icm.app.addNewForm.AddClaim;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.services.ClaimManager;

public class ClaimData extends TableDataPanel<Claim> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ClaimData(Consumer<Map<String, String>> updateHelperText, Consumer<String> updateInfoBox) {
        super(List.of(
                    String.format("%-12s", "ID"),
                    String.format("%-12s", "Claim date"),
                    String.format("%-20s", "Insured person"),
                    String.format("%-12s", "Card number"),
                    String.format("%-12s", "Exam date"),
                    String.format("Documents"),
                    String.format("%-12s", "Claim amount"),
                    String.format("%-8s", "Status"),
                    String.format("%-30s", "Receiver banking info")
                ),
                claim -> new String[] {
                        claim.getId(),
                        claim.getClaimDate().format(formatter),
                        claim.getInsuredPerson(),
                        claim.getCardNumber(),
                        claim.getExamDate().format(formatter),
                        claim.getDocuments().toString(),
                        String.valueOf(claim.getClaimAmount()),
                        claim.getStatus().toString(),
                        claim.getReceiverBankingInfo()
                },
                ClaimManager.getInstance(),
                updateHelperText,
                updateInfoBox);
    }

    @Override
    public synchronized Border withBorder() {
        return withBorder(Borders.singleLine("[4] Claims"));
    }

    @Override
    protected Map<String, String> useHelperText() {
        Map<String, String> helperText = new LinkedHashMap<>();
        helperText.put("Add", "a");
        // helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        // helperText.put("DeleteAll", "<ctrl-D>");
        helperText.put("Set done", "s");

        return helperText;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 'a':
                ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new AddClaim());
                update();
                return true;
            // case 'd':
            //     {
            //         String id = table.getTableModel().getRow(table.getSelectedRow()).get(1);
            //         table.getTableModel().removeRow(table.getSelectedRow());
            //         DependentManager.getInstance().delete(id.trim());
            //     }
            //     return true;
            case null:
                break;
            default:
                break;
        }
        return super.handleInput(key);
    }

}
