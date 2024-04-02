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
import com.hankaji.icm.app.addNewForm.ClaimForm;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.claim.ClaimStatus;
import com.hankaji.icm.components.ProductForm.ProcessType;
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
                String.format("%-30s", "Receiver banking info")),
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
    protected String getObjectInfo() {
        return ClaimManager.getInstance().get(
                table.getTableModel().getRow(table.getSelectedRow()).get(0)).showInfoBox();
    }

    @Override
    protected Map<String, String> useHelperText() {
        Map<String, String> helperText = new LinkedHashMap<>();
        helperText.put("Add", "a");
        helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        // helperText.put("DeleteAll", "<ctrl-D>");
        helperText.put("Set done", "s");

        return helperText;
    }

    private List<String> separateBanking(String bankInfo) {
        return List.of(bankInfo.split("-"));
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 'a':
                ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new ClaimForm());
                update();
                return true;
            case 'e': {
                String id = table.getTableModel().getRow(table.getSelectedRow()).get(0);
                Claim currClaim = ClaimManager.getInstance().get(id.trim());

                List<String> bankInfo = separateBanking(currClaim.getReceiverBankingInfo());

                ClaimForm form = new ClaimForm(
                        currClaim.getExamDate(),
                        String.join(", ", currClaim.getDocuments()),
                        String.valueOf(currClaim.getClaimAmount()),
                        currClaim.getStatus(),
                        bankInfo.get(0),
                        bankInfo.get(1),
                        bankInfo.get(2));

                form.setType(ProcessType.EDIT);
                form.editData(id);

                ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(form);
            }
                update();
                return true;
            case 'd': {
                String id = table.getTableModel().getRow(table.getSelectedRow()).get(0);
                table.getTableModel().removeRow(table.getSelectedRow());
                ClaimManager.getInstance().delete(id.trim());
            }
                return true;
            case 's': {
                String id = table.getTableModel().getRow(table.getSelectedRow()).get(0);
                ClaimManager.getInstance().get(id.trim()).setStatus(ClaimStatus.DONE);
                update();
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
