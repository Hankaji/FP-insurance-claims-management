package com.hankaji.icm.app.home.components.tableData;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

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
import com.hankaji.icm.components.ProductForm.ProcessType;
import com.hankaji.icm.services.ClaimManager;

/**
 * This class represents a panel that displays claim data in a table format.
 * It extends the TableDataPanel class and provides specific implementation for claim data.
 */
public class ClaimData extends TableDataPanel<Claim> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructs a ClaimData object with the specified updateHelperText and updateInfoBox consumers.
     * 
     * @param updateHelperText A consumer that updates the helper text.
     * @param updateInfoBox A consumer that updates the info box.
     */
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
    protected Map<String, String> useHelperText() {
        Map<String, String> helperText = new LinkedHashMap<>();
        helperText.put("Add", "a");
        helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        // helperText.put("DeleteAll", "<ctrl-D>");
        helperText.put("Set done", "s");

        return helperText;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 's': {
                String id = table.getTableModel().getRow(table.getSelectedRow()).get(0);
                ClaimManager.getInstance().getById(id.trim()).get().setStatus(Claim.Status.REJECTED);
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

    @Override
    protected void onAddKeyPressed() {
        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(new ClaimForm());
    }

    @Override
    protected void onEditKeyPressed() {
        String id = table.getTableModel().getRow(table.getSelectedRow()).get(0);
        Claim currClaim = ClaimManager.getInstance().getById(id.trim()).get();

        ClaimForm form = new ClaimForm(currClaim);

        form.setType(ProcessType.EDIT);

        ((WindowBasedTextGUI) getTextGUI()).addWindowAndWait(form);
    }

}