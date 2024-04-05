package com.hankaji.icm.app.addNewForm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import static com.hankaji.icm.lib.Utils.nullOrDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.hankaji.icm.components.ProductForm;
import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.ClaimManager;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

/**
 * Represents a form for adding a new claim.
 * Extends the {@link ProductForm} class.
 */
public class ClaimForm extends ProductForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    ArrayList<Customer> customers;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Claim oldClaim;

    // Components

    final ComboBox<String> insuredPerson = new ComboBox<>();

    final TextBox examDateInput = new TextBox();

    final TextBox documents = new TextBox().setValidationPattern(Pattern.compile("^[a-zA-Z0-9,\\. ]+$"));

    final TextBox claimAmountInput = new TextBox().setValidationPattern(Pattern.compile("[0-9]*"));

    final ComboBox<Claim.Status> status = new ComboBox<>(
            Claim.Status.PENDING,
            Claim.Status.APPROVED,
            Claim.Status.REJECTED);

    final TextBox bankInput = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));
    final TextBox nameInput = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));
    final TextBox numberInput = new TextBox().setValidationPattern(Pattern.compile("[0-9]*"));

    /**
     * Constructs a new instance of the ClaimForm class.
     * Calls the parameterized constructor with a null oldClaim.
     */
    public ClaimForm() {
        this(null);
    }

    /**
     * Constructs a new instance of the ClaimForm class with the specified oldClaim.
     * 
     * @param oldClaim the claim to be used as placeholder data
     */
    public ClaimForm(Claim oldClaim) {
        super("Add new claim");
        this.oldClaim = oldClaim;

        // Get all customers
        customers = Stream.concat(
                depMan.getAll().stream(),
                policyHolderMan.getAll().stream())
                .collect(Collectors.toCollection(ArrayList::new));

        // Filter customer with no card
        customers = customers.stream()
                .filter(c -> c.getInsuranceCard() != null)
                .collect(Collectors.toCollection(ArrayList::new));

        // Customer list
        for (Customer c : customers) {
            // Add customer to the owner list
            String idString = " (" + c.getId() + ")";
            insuredPerson.addItem(c.getName() + idString);
        }
        if (insuredPerson.getItemCount() == 0) {
            insuredPerson.addItem("No person available");
        }
        addField("Insured person", insuredPerson);

        // Exam date
        addField("Exam date (dd/mm/yyyy)", examDateInput.setText(nullOrDefault(() -> oldClaim.getExamDate().format(formatter))));

        // Add documents
        addField("List of documents (comma separated)", documents.setText(
                nullOrDefault(() -> String.join(", ", separateDoc(oldClaim.getDocuments().stream().collect(Collectors.joining(", ")))))));

        // Claim amount
        addField("Claim amount (in $)", claimAmountInput.setText(nullOrDefault(() -> String.valueOf(oldClaim.getClaimAmount()))));

        // Status
        addField("Status", status);
        status.setSelectedItem(nullOrDefault(() -> oldClaim.getStatus(), Claim.Status.PENDING));

        // Separator
        inputFields.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData(2)));

        // Backing info
        addField("Banking info", bankInput.setText(nullOrDefault(() -> oldClaim.getReceiverBankingInfo().split("-")[0])));
        addField("Name", nameInput.setText(nullOrDefault(() -> oldClaim.getReceiverBankingInfo().split("-")[1])));
        addField("Number", numberInput.setText(nullOrDefault(() -> oldClaim.getReceiverBankingInfo().split("-")[2])));

    }

    @Override
    protected boolean onSubmit() throws Exception {

        // ID
        String id = "f-" + ID.generateID(10);

        // Claimdate
        LocalDateTime claimDate = LocalDateTime.now();

        // Get customer and card
        String selectedInsured;
        Customer insuredCustomer;
        
        String selectedCard;
        InsuranceCard card;
        try {
            selectedInsured = insuredPerson.getSelectedItem().replaceAll("\\(.*?\\)", "").trim();
            insuredCustomer = customers.stream()
                    .filter(c -> c.getName().equals(selectedInsured))
                    .findFirst()
                    .get();

            selectedCard = insuredCustomer.getInsuranceCard().getCardNumber();
            card = icm.getById(selectedCard).orElse(null);
        } catch (Exception e) {
            throw new Exception("Either people is blank or the insured person doens't has a card. Please create at least one.");
        }

        // Exam date
        LocalDateTime examDate = LocalDate.parse(examDateInput.getText(), formatter).atStartOfDay();

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
        insuredCustomer.getClaims().add(newClaim);

        return true;
    }

    @Override
    protected boolean onEdit() throws Exception {

        // Get claim
        if (oldClaim == null)
            throw new Exception("Claim cannot be found.");

        // Documents
        List<String> newDocList = separateDoc(documents.getText());
        newDocList = reformatDoc(oldClaim.getId(), oldClaim.getCardNumber(), newDocList);

        // Banking info
        String bankInfo = String.format("%s-%s-%s",
                bankInput.getText().trim(),
                nameInput.getText().trim(),
                numberInput.getText().trim());

        // Exam date
        LocalDateTime examDate = LocalDate.parse(examDateInput.getText(), formatter).atStartOfDay();

        // Update claim
        oldClaim.setExamDate(examDate);
        oldClaim.setDocuments(new ArrayList<>(newDocList));
        oldClaim.setClaimAmount(Integer.parseInt(claimAmountInput.getText()));
        oldClaim.setStatus(status.getSelectedItem());
        oldClaim.setReceiverBankingInfo(bankInfo);

        ClaimManager.getInstance().update(oldClaim);

        return true;
    }

    /**
     * Reformat the document list.
     * <h4>Example:</h4>
     * <p>myDoc1 => 1234567890_1234567_myDoc1.pdf</p>
     * 
     * @param claimID
     * @param cardNumber
     * @param docList
     * @return
     */
    private List<String> reformatDoc(String claimID, String cardNumber, List<String> docList) {
        return docList.stream().map(
                doc -> String.format("%s_%s_%s.pdf", claimID, cardNumber, doc))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    
    /**
     * Separate the document list.
     * 
     * <h4>Tested format: </h4>
     * <p> doc1, doc2, doc3 </p>
     * <p> doc1.pdf, doc2.pdf, doc3.pdf </p>
     * <p> 1_1_doc1.pdf, 1_1_doc2.pdf, 1_1_doc3.pdf </p>
     * @param docList
     * @return
     */
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
