package com.hankaji.icm.app.home;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.app.NoDecorationWindow;
import com.hankaji.icm.app.home.components.HelperText;
import com.hankaji.icm.app.home.components.PopupHelpMsg;
import com.hankaji.icm.app.home.components.WelcomePanel;
import com.hankaji.icm.app.home.components.tableData.DependentData;
import com.hankaji.icm.app.home.components.tableData.InsuranceCardData;
import com.hankaji.icm.app.home.components.tableData.PolicyHolderData;
import com.hankaji.icm.app.home.components.tableData.ClaimData;
import com.hankaji.icm.app.home.components.tableData.TableDataPanel;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.claim.Claim;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;

// Utilites import
import static com.hankaji.icm.lib.Utils.extendsCollection;
import static com.hankaji.icm.lib.Utils.LayoutUtils.*;


/**
 * The main window of the insurance claims management application.
 * It extends the NoDecorationWindow class and provides functionality for switching between panels,
 * displaying helper text, and handling keyboard events.
 * 
 * This window consists of a master panel that contains a layout panel and a help panel.
 * The layout panel is divided into two sections: the left section contains the menu and the right section
 * contains the content panel that displays the data.
 * 
 * Supports keyboard shortcuts for navigating between panels, closing the application,
 * displaying information, and showing help messages.
 * 
 * The HomeListener class is a WindowListener that listens for keyboard events and handles the input.
 * It provides methods for switching between panels, closing the application, displaying information,
 * and showing help messages.
 * 
 * @author Thai Phuc
 */
public class Home extends NoDecorationWindow {

    private Map<String, String> helperText = new LinkedHashMap<>();
    {
        helperText.put("Quit", "Q");
        helperText.put("Move between panels", "[1]-[4]");
        helperText.put("Navigation", "<Arrow keys>");
        helperText.put("Info", "i");
    }

    // Fields
    TableDataPanel<?> currentDataShown;

    Config conf = Config.getInstance();

    // Components
    Panel masterPanel = new Panel(createGridLayoutwithCustomMargin(1, 0));

    Panel layoutPanel = new Panel(createGridLayoutwithCustomMargin(2, 0));

    Panel helpPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

    Panel infoBox = new Panel(new LinearLayout(Direction.VERTICAL));

    Component borderedInfoBox = infoBox.withBorder(Borders.singleLine("Info")).setLayoutData(
            GridLayout.createLayoutData(
                    GridLayout.Alignment.FILL,
                    GridLayout.Alignment.FILL,
                    true,
                    true));

    HelperText generalHelp = new HelperText(helperText);

    HelperText panelSpecificHelp = new HelperText();

    WelcomePanel welcomePanel = new WelcomePanel(this::updateHelperText);

    TableDataPanel<Dependent> dependentDataPanel = new DependentData(this::updateHelperText, this::updateInfoBox);

    TableDataPanel<PolicyHolder> policyHolderDataPanel = new PolicyHolderData(this::updateHelperText,
            this::updateInfoBox);

    TableDataPanel<InsuranceCard> insuranceCardDataPanel = new InsuranceCardData(this::updateHelperText,
            this::updateInfoBox);

    TableDataPanel<Claim> claimDataPanel = new ClaimData(this::updateHelperText, this::updateInfoBox);

    /**
     * Constructs a new Home object with the specified title.
     * This window is full screen and contains a master panel that contains a layout panel and a help panel.
     */
    public Home() {
        super("Home");
        setHints(extendsCollection(getHints(), Hint.FULL_SCREEN));

        // --------------------------------------------------
        // Panel layout
        // --------------------------------------------------
        // Verticle container with the top containing information and the bottom
        // containing the helper text (shortcut, etc...)
        masterPanel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        // Horizontal container with the left containing the menu and the right
        // containing the content
        layoutPanel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        // --------------------------------------------------
        // Keyboards events
        // --------------------------------------------------
        addWindowListener(new HomeListener());

        // --------------------------------------------------
        // Add components
        // --------------------------------------------------
        masterPanel.addComponent(layoutPanel);
        masterPanel.addComponent(helpPanel);

        helpPanel.addComponent(generalHelp);
        helpPanel.addComponent(new Separator(Direction.VERTICAL));
        helpPanel.addComponent(panelSpecificHelp);

        layoutPanel.addComponent(welcomePanel.withBorder(Borders.singleLineBevel()));

        setComponent(masterPanel);
    }

    /**
     * Switches to the specified panel.
     * 
     * @param newPanel The panel to switch to.
     */
    private void switchWindow(TableDataPanel<?> newPanel) {
        if (currentDataShown == newPanel)
            return;
        currentDataShown = newPanel;
    }

    /**
     * Renders the specified panel.
     * 
     * @param panelToShow The panel to render.
     */
    private void renderDataPanel(TableDataPanel<?> panelToShow) {
        layoutPanel.removeAllComponents().addComponent(panelToShow.withBorder().setLayoutData(
                GridLayout.createLayoutData(
                        GridLayout.Alignment.FILL,
                        GridLayout.Alignment.FILL,
                        true,
                        true)));
    }

    /**
     * Switches to the specified panel and renders it.
     * 
     * @param newPanel
     */
    private void switchAndRerender(TableDataPanel<?> newPanel) {
        switchWindow(newPanel);
        renderDataPanel(newPanel);
    }

    /**
     * Updates the helper text with the specified text.
     * 
     * @param helperText The helper text to update.
     */
    private void updateHelperText(Map<String, String> helperText) {
        panelSpecificHelp.updateHelperText(helperText);
    }

    /**
     * Updates the information box with the specified information.
     * 
     * @param info The information to update.
     */
    private void updateInfoBox(String info) {
        infoBox.removeAllComponents().addComponent(new Label(info));
    }

    /**
     * <pre>
     * The HomeListener class is a WindowListener that listens for keyboard events that are used to switch between panels and to close the application.
     * Panels dedicated input are handled by the panels themselves. Check handleInput(KeyStroke key) in the Panel class for example.
     * </pre>
     * 
     * @author Thai Phuc
     */
    private class HomeListener implements WindowListener {

        @Override
        public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {

            switch (keyStroke.getKeyType()) {
                default:
                    break;
            }
            switch (keyStroke.getCharacter()) {
                case 'Q':
                    close();
                    return;
                case 'i':
                    if (layoutPanel.containsComponent(infoBox)) {
                        layoutPanel.removeComponent(borderedInfoBox);
                    } else {
                        layoutPanel.addComponent(borderedInfoBox);
                    }
                    return;
                case 'h':
                    getTextGUI().addWindowAndWait(new PopupHelpMsg());
                    return;
                case '1':
                    switchAndRerender(dependentDataPanel);
                    return;
                case '2':
                    switchAndRerender(policyHolderDataPanel);
                    return;
                case '3':
                    switchAndRerender(insuranceCardDataPanel);
                    return;
                case '4':
                    switchAndRerender(claimDataPanel);
                    return;
                case null:
                    return;
                default:
                    break;
            }

            // Genuenly don't know why TableDataPanel can handle key but this Panel can't. I'm going insane
            welcomePanel.handleInput(keyStroke);
        }

        @Override
        public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
        }

        @Override
        public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
            welcomePanel.onResized(newSize);
        }

        @Override
        public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
        }

    }

}
