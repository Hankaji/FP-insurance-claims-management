package com.hankaji.icm.app.addNewForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextBox;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.claim.ClaimStatus;
import com.hankaji.icm.components.AddNewForm;
import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.ClaimManager;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class AddClaim extends AddNewForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    ArrayList<Customer> customers;

    // Components

    final ComboBox<String> insuredPerson = new ComboBox<>();

    final ComboBox<String> cardList = new ComboBox<>();

    final TextBox examDate = new TextBox();

    final TextBox documents = new TextBox().setValidationPattern(Pattern.compile("^[a-zA-Z0-9,\\. ]+$"));

    final TextBox claimAmountInput = new TextBox().setValidationPattern(Pattern.compile("[0-9]*"));

    final ComboBox<ClaimStatus> status = new ComboBox<>(
            ClaimStatus.NEW,
            ClaimStatus.PROCESSING,
            ClaimStatus.DONE);

    final TextBox bankInput = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));
    final TextBox nameInput = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));
    final TextBox numberInput = new TextBox().setValidationPattern(Pattern.compile("[0-9]*"));

    public AddClaim() {
        this("", "", ClaimStatus.NEW, "", "", "");
    }

    /**
     * Add new claim with Placeholder (PH) values
     * 
     * @param documentPH
     * @param claimAmountPH
     * @param statusPH
     * @param bankPH
     * @param namePH
     * @param numberPH
     */
    public AddClaim(String documentPH, String claimAmountPH, ClaimStatus statusPH, String bankPH, String namePH, String numberPH) {
        super("Add new claim");

        // Get all customers
        customers = Stream.concat(
                depMan.getAll().stream(),
                policyHolderMan.getAll().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        // Customer list
        for (Customer c : customers) {
            // Add customer to the owner list
            insuredPerson.addItem(c.getName() + " (" + c.getId() + ")");
        }
        if (insuredPerson.getItemCount() == 0) {
            insuredPerson.addItem("No person available");
        }
        addField("Insured person", insuredPerson);

        // Card list
        for (InsuranceCard c : icm.getAll()) {
            cardList.addItem(c.getCardNumber() + (c.getCardHolder().isBlank() ? "" : " (" + c.getCardHolder() + ")"));
        }
        if (cardList.getItemCount() == 0) {
            cardList.addItem("No card available");
        }
        addField("Card", cardList);

        // Add documents
        addField("List of documents (comma separated)", documents.setText(
            documentPH.isBlank() ? "" : String.join(", ", separateDoc(documentPH))));

        // Claim amount
        addField("Claim amount (in $)", claimAmountInput.setText(claimAmountPH));

        // Status
        addField("Status", status);
        status.setSelectedItem(statusPH);

        // Separator
        inputFields.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData(2)));

        // Backing info
        addField("Banking info", bankInput.setText(bankPH));
        addField("Name", nameInput.setText(namePH));
        addField("Number", numberInput.setText(numberPH));

    }

    @Override
    protected boolean onSubmit() throws Exception {
        // ID
        String id = "f-" + ID.generateID(10);

        // Claimdate
        LocalDateTime claimDate = LocalDateTime.now();

        // Get customer
        String selectedInsured;
        try {
            selectedInsured = insuredPerson.getSelectedItem().replaceAll("\\(.*?\\)", "").trim();
            customers.stream()
                    .filter(c -> c.getName().equals(selectedInsured))
                    .findFirst()
                    .get();
        } catch (Exception e) {
            throw new Exception("Insurance people cannot be blank, please create at least one");
        }

        // Get card
        String selectedCard = cardList.getSelectedItem().replaceAll("\\(.*?\\)", "").trim();
        InsuranceCard card = icm.get(selectedCard);
        if (card == null)
            throw new Exception("Card cannot be blank, please create at least one");

        // Exam date
        LocalDateTime examDate = LocalDateTime.now();

        // Documents
        List<String> newDocList = separateDoc(documents.getText());
        newDocList = reformatDoc(id, selectedCard, newDocList);

        // Banking info
        String bankInfo = String.format("%s-%s-%s",
                bankInput.getText().trim(),
                nameInput.getText().trim(),
                numberInput.getText().trim());

        Claim newClaim = Claim.builder()
                .setId(id)
                .setClaimDate(claimDate)
                .setInsuredPerson(selectedInsured)
                .setCardNumber(card.getCardNumber())
                .setExamDate(examDate)
                .setDocuments(new ArrayList<>(newDocList))
                .setClaimAmount(Integer.parseInt(claimAmountInput.getText()))
                .setStatus(status.getSelectedItem())
                .setReceiverBankingInfo(bankInfo)
                .build();

        ClaimManager.getInstance().add(newClaim);

        return true;
    }

    private List<String> reformatDoc(String claimID, String cardNumber, List<String> docList) {
        return docList.stream().map(
                doc -> String.format("%s_%s_%s.pdf", claimID, cardNumber, doc))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // doc1, doc2, doc3
    // doc1.pdf, doc2.pdf, doc3.pdf
    // 1_1_doc1.pdf, 1_1_doc2.pdf, 1_1_doc3.pdf
    private List<String> separateDoc(String docList) {
        if (docList.isBlank())
            return new ArrayList<>();

        List<String> newDoclist;

        newDoclist = Arrays.asList(docList.split(",")).stream().map(
                doc -> {
                    // split the underscore
                    String[] temp = doc.split("_");
                    if (temp.length > 1)
                        doc = temp[temp.length - 1];

                    String[] temp2 = doc.split(".");
                    if (temp2.length > 1)
                        doc = temp2[0];

                    return doc.trim();
                }).collect(Collectors.toCollection(ArrayList::new));

        return newDoclist;
    }

}
