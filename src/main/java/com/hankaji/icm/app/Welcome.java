package com.hankaji.icm.app;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.lib.Utils;

public class Welcome extends NoDecorationWindow {
    private final String welcomeMessage =
                "  ___                                             ____ _       _             __  __                                                   _   \n" + //
                " |_ _|_ __  ___ _   _ _ __ __ _ _ __   ___ ___   / ___| | __ _(_)_ __ ___   |  \\/  | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ \n" + //
                "  | || '_ \\/ __| | | | '__/ _` | '_ \\ / __/ _ \\ | |   | |/ _` | | '_ ` _ \\  | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|\n" + //
                "  | || | | \\__ \\ |_| | | | (_| | | | | (_|  __/ | |___| | (_| | | | | | | | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ \n" + //
                " |___|_| |_|___/\\__,_|_|  \\__,_|_| |_|\\___\\___|  \\____|_|\\__,_|_|_| |_| |_| |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__|\n" + //
                "                                                                                                      |___/                               ";

    private final String fallbackWelcomeMessage = 
                "  ___ ____ __  __ \n" + //
                " |_ _/ ___|  \\/  |\n" + //
                "  | | |   | |\\/| |\n" + //
                "  | | |___| |  | |\n" + //
                " |___\\____|_|  |_|\n" + //
                "                  ";

    private Map<String, Runnable> menuOptions = new LinkedHashMap<>();
    {
        menuOptions.put("View Claims", new Runnable() {
            @Override
            public void run() {
            }
        });

        menuOptions.put("Add Claim", new Runnable() {
            @Override
            public void run() {
            }
        });

        menuOptions.put("Exit", new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }
    private int selected = 0;

    Panel menuPanel = new Panel(new LinearLayout(Direction.VERTICAL));

    public Welcome() {
        super();
        
        setHints(Utils.extendsCollection(getHints(), Hint.FULL_SCREEN));

        Panel panel = new Panel(new GridLayout(1));

        // Add a welcome label to the panel
        Label welcomeMsg = new Label(welcomeMessage);
        welcomeMsg.setForegroundColor(ANSI.DEFAULT);
        welcomeMsg.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true, 1, 1));
        welcomeMsg.addTo(panel);

        // Add menu option
        ActionListBox actionListBox = new ActionListBox();
        for (var entry : menuOptions.entrySet()) {
            actionListBox.addItem("", entry.getValue());
        }
        actionListBox.addTo(panel);

        // Add menu label
        menuPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.BEGINNING, true, true, 1, 1));
        
        renderMenuOptions();

        addWindowListener(new WindowListener() {

            @Override
            public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
                switch (keyStroke.getKeyType()) {
                    case ArrowUp:
                        if (selected > 0) {
                            selected--;
                        }
                        break;
                    case ArrowDown:
                        if (selected < menuOptions.size() - 1) {
                            selected++;
                        }
                        break;
                    default:
                        break;
                }
                renderMenuOptions();
            }

            @Override
            public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {}

            @Override
            public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
                if (newSize.getColumns() < 145) {
                    welcomeMsg.setText(fallbackWelcomeMessage);
                } else {
                    welcomeMsg.setText(welcomeMessage);
                }
            }

            @Override
            public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {}
            
        });

        menuPanel.addTo(panel);
        
        setComponent(panel);

    }

    // Menu Options is added separately because it is dynamic, which requires re-rendering every time the selected option changes
    private void renderMenuOptions() {
        menuPanel.removeAllComponents();

        int labelIdx = 0;
        for (var entry : menuOptions.entrySet()) {
            // Create new label and center it
            Label menuLabel = new Label(entry.getKey());
            menuLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            // Highlight the selected option
            if (labelIdx == selected) {
                menuLabel.setText("> " + entry.getKey() + " <");
                menuLabel.setForegroundColor(ANSI.CYAN);
            }
            menuLabel.addTo(menuPanel);

            // Add empty space between options
            EmptySpace emptySpace = new EmptySpace();
            emptySpace.addTo(menuPanel);
            
            labelIdx++;
        }
    }
}
