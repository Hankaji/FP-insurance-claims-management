package com.hankaji.icm.app.home;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.app.NoDecorationWindow;
import com.hankaji.icm.app.addNewForm.AddPolicyHolder;
import com.hankaji.icm.app.home.components.DependentData;
import com.hankaji.icm.app.home.components.PolicyHolderData;
import com.hankaji.icm.app.home.components.TableDataPanel;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;

// Utilites import
import static com.hankaji.icm.lib.Utils.extendsCollection;
import static com.hankaji.icm.lib.Utils.LayoutUtils.*;

public class Home extends NoDecorationWindow {

    private Map<String, String> helperText = new LinkedHashMap<>();
    {
        helperText.put("Quit", "q");
        helperText.put("Move between panels", "[1]-[5]");
        helperText.put("Navigation", "<Arrow keys>");
        helperText.put("Add", "a");
        helperText.put("Edit", "e");
        helperText.put("Delete", "d");
        helperText.put("DeleteAll", "<ctrl-D>");
        helperText.put("Info", "i");
    }

    // Fields
    TableDataPanel<?> currentDataShown;

    // Layout 
    Panel masterPanel = new Panel(createGridLayoutwithCustomMargin(1, 0));

    Panel layoutPanel = new Panel(createGridLayoutwithCustomMargin(2, 0));

    Panel helperLabelPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));

    TableDataPanel<Dependent> dependentDataPanel = new DependentData();

    TableDataPanel<PolicyHolder> policyHolderDataPanel = new PolicyHolderData();

    // COnfiguration file
    Config conf = Config.getInstance();

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
        layoutPanel.setPreferredSize(new TerminalSize(10, 10));

        // --------------------------------------------------
        // Left Panel
        // --------------------------------------------------


        // --------------------------------------------------
        // Helper text
        // --------------------------------------------------
        int idx = 0;
        for (Map.Entry<String, String> entry : helperText.entrySet()) {
            Label inforLabel = new Label(idx == 0 ? 
                (entry.getKey() + ":") 
                  : 
                ("| " + entry.getKey() + ":"));
            Label keyLabel = new Label(entry.getValue());
            keyLabel.setForegroundColor(TextColor.Factory.fromString(conf.getTheme().getHighlightedFg()));
            
            helperLabelPanel.addComponent(inforLabel);
            helperLabelPanel.addComponent(keyLabel);

            idx++;
        }

        // --------------------------------------------------
        // Keyboards events
        // --------------------------------------------------
        addWindowListener(new HomeListener());

        // --------------------------------------------------
        // Add components
        // --------------------------------------------------

        masterPanel.addComponent(layoutPanel);
        masterPanel.addComponent(helperLabelPanel);

        currentDataShown = dependentDataPanel;
        renderDataPanel(currentDataShown);

        setComponent(masterPanel);
    }

    private void switchWindow(TableDataPanel<?> newPanel) {
        if (currentDataShown == newPanel) return;
        currentDataShown = newPanel;
    }

    private void renderDataPanel(TableDataPanel<?> panelToShow) {
        layoutPanel.removeAllComponents().addComponent(panelToShow.withBorder());
    }

    private void switchAndRerender(TableDataPanel<?> newPanel) {
        switchWindow(newPanel);
        renderDataPanel(newPanel);
    }

    private class HomeListener implements WindowListener {

        @Override
        public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
            
            switch (keyStroke.getKeyType()) {
                default:
                    break;
            }
            switch (keyStroke.getCharacter()) {
                case 'q':
                    close();
                    return;
                case 'a':
                    // getTextGUI().addWindowAndWait(new AddDependent(""));
                    getTextGUI().addWindowAndWait(new AddPolicyHolder());
                    return;
                case '1':
                    
                    switchAndRerender(dependentDataPanel);
                    return;
                case '2':
                    switchAndRerender(policyHolderDataPanel);
                    return;
                case '3':
                    // switchAndRerender(policyHolderDataPanel);
                    return;
                case '4':
                    // switchAndRerender(policyHolderDataPanel);
                    return;
                case null:
                    return;
                default:
                    break;
            }
        }

        @Override
        public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
        }

        @Override
        public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
        }

        @Override
        public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
        }
        
    }

}
