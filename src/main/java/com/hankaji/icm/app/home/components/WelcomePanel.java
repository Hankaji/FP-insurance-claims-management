package com.hankaji.icm.app.home.components;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import static com.hankaji.icm.lib.Utils.useHex;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.lib.Utils;
import com.hankaji.icm.services.ClaimManager;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

/**
 * A custom panel component that displays the welcome message.
 */
public class WelcomePanel extends Panel {
    private final String welcomeMessage = " ___                                             ____ _       _             __  __                                                   _   \n"
            + //
            "|_ _|_ __  ___ _   _ _ __ __ _ _ __   ___ ___   / ___| | __ _(_)_ __ ___   |  \\/  | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ \n"
            + //
            " | || '_ \\/ __| | | | '__/ _` | '_ \\ / __/ _ \\ | |   | |/ _` | | '_ ` _ \\  | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|\n"
            + //
            " | || | | \\__ \\ |_| | | | (_| | | | | (_|  __/ | |___| | (_| | | | | | | | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ \n"
            + //
            "|___|_| |_|___/\\__,_|_|  \\__,_|_| |_|\\___\\___|  \\____|_|\\__,_|_|_| |_| |_| |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__|\n"
            + //
            "                                                                                                     |___/                               ";

    private final String fallbackWelcomeMessage = " ___ ____ __  __ \n" + //
            "|_ _/ ___|  \\/  |\n" + //
            " | | |   | |\\/| |\n" + //
            " | | |___| |  | |\n" + //
            "|___\\____|_|  |_|\n" + //
            "                 ";

    public static final String helpMessage = "To get started, press from [1] to [4] to navigate between panels. Press [i] to get more information of cell. Press [q] to quit the application. Press <arrow keys> to navigate within the panel. General/Specific keybinds are shown below this panel. Press [h] to get this help message.";

    private Map<String, String> helperText = new LinkedHashMap<>();
    {
        helperText.put("Populate data", "p");
    }

    Config conf = Config.getInstance();

    private Label welcomeMessageLabel = new Label(welcomeMessage);

    private Label helpMessageLabel = new Label(helpMessage);

    /**
     * Constructs a new WelcomePanel instance with the specified updateHelperText
     * 
     * @param updateHelperText the consumer that updates the helper text
     */
    public WelcomePanel(Consumer<Map<String, String>> updateHelperText) {
        super(Utils.LayoutUtils.createGridLayoutwithCustomMargin(1, 1));

        setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.FILL, GridLayout.Alignment.FILL, true, true));

        addComponent(welcomeMessageLabel);

        addComponent(new EmptySpace());
        addComponent(new Label("Copyright 2024 Hoang Thai Phuc. All rights reserved"));

        addComponent(new EmptySpace());
        Panel githubPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        githubPanel.addComponent(new Label("GitHub:"));
        githubPanel.addComponent(new Label("https://github.com/Hankaji").addStyle(SGR.UNDERLINE));
        addComponent(githubPanel);

        addComponent(new EmptySpace());
        Panel projectPagePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        projectPagePanel.addComponent(new Label("Project repo:"));
        projectPagePanel.addComponent(
                new Label("https://github.com/Hankaji/FP-insurance-claims-management").addStyle(SGR.UNDERLINE));
        addComponent(projectPagePanel);

        addComponent(new EmptySpace());
        addComponent(helpMessageLabel);

        addComponent(new EmptySpace());
        addComponent(new Label(
                "15 default objects are available for testing. You can press [p] to populate the database with these objects."));
        addComponent(new Label("*Note: This action will overwrite the current database. Please use with caution.")
                .addStyle(SGR.ITALIC).setForegroundColor(useHex(conf.getTheme().getErrorFg())));

        updateHelperText.accept(helperText);
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 'p':
                // Populate the database with default objects
                MessageDialogButton confirmation = MessageDialog.showMessageDialog((WindowBasedTextGUI) getTextGUI(), "Confirmation",
                        "What you are about to do will overwrite the current database. Are you sure you want to continue?", 
                        MessageDialogButton.Cancel,
                        MessageDialogButton.Continue);
                switch (confirmation) {
                    case Continue:
                        // Populate the database
                        // Copy all files in ./data/default to ./data
                        try {
                            FileUtils.copyDirectory(new File("./data/default"), new File("./data"));
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error populating the database");
                        }
                        DependentManager.getInstance().loadData();
                        PolicyHolderManager.getInstance().loadData();
                        InsuranceCardManager.getInstance().loadData();
                        ClaimManager.getInstance().loadData();
                        break;
                    default:
                        break;
                }
                return true;
            case null:
                break;
            default:
                break;
        }
        return super.handleInput(key);
    }

    /**
     * Updates the welcome message and help message when the terminal is resized.
     * 
     * @param newSize the new terminal size
     */
    public void onResized(TerminalSize newSize) {
        if (newSize.getColumns() < 145) {
            welcomeMessageLabel.setText(fallbackWelcomeMessage);
        } else {
            welcomeMessageLabel.setText(welcomeMessage);
        }
        helpMessageLabel.setPreferredSize(newSize.withRows(helpMessage.length() / newSize.getColumns() + 2));
    }

}
