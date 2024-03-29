package com.hankaji.icm.app;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableHeaderRenderer;
import com.googlecode.lanterna.input.KeyStroke;

import com.hankaji.icm.config.Config;

// Utilites import
import static com.hankaji.icm.lib.Utils.extendsCollection;
import static com.hankaji.icm.lib.Utils.LayoutUtils.*;

public class Home extends NoDecorationWindow {

    private List<String> tempData = List.of("Hoang Thai Phuc", "s3978081", "Single");
    private List<String> tempData2 = List.of("Phuc Hoang Thai", "s1808793", "Single");

    private String helperText = "Quit: q | Move: hjkl / <Arrow keys>";

    private Config conf = Config.getINSTANCE();

    public Home() {
        super("Home");
        setHints(extendsCollection(getHints(), Hint.FULL_SCREEN));

        // --------------------------------------------------
        // Panel layout
        // --------------------------------------------------
        // Verticle container with the top containing information and the bottom
        // containing the helper text (shortcut, etc...)
        Panel masterPanel = new Panel(createGridLayoutwithCustomMargin(1, 0));
        masterPanel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        // Horizontal container with the left containing the menu and the right
        // containing the content
        Panel layoutPanel = new Panel(createGridLayoutwithCustomMargin(2, 0));
        layoutPanel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));
        layoutPanel.setPreferredSize(new TerminalSize(10, 10));

        // --------------------------------------------------
        // Left Panel
        // --------------------------------------------------
        Panel leftPanel = new Panel(new GridLayout(1));
        leftPanel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        List<String> tableTitles = List.of(
                String.format("%-30s", "Name"),
                String.format("%-10s", "ID"),
                String.format("%-20s", "Status"));

        // Tabel header
        Panel tableHeaderPanel = new Panel(createGridLayoutwithCustomMargin(tableTitles.size(), 0));
        for (String title : tableTitles) {
            Label headerLabel = new Label(title);
            headerLabel.setForegroundColor(TextColor.Factory.fromString(conf.getTheme().getHighlightedFg()));
            tableHeaderPanel.addComponent(headerLabel);
        }

        // Table splitter
        Separator tableSplitter = new Separator(Direction.HORIZONTAL);
        tableSplitter.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.CENTER,
                true,
                false));

        // Table cells
        Table<String> customerTable = new Table<String>(
                tableTitles.toArray(new String[0]));
        customerTable.getTableModel().addRow(tempData);
        customerTable.getTableModel().addRow(tempData2);

        customerTable.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        customerTable.setTableHeaderRenderer(new DisabledTableHeaderRenderer());

        Border leftPanelBordered = leftPanel.withBorder(Borders.singleLineBevel("Students"));

        // --------------------------------------------------
        // Helper text
        // --------------------------------------------------
        Panel helperLabelPanel = new Panel(new GridLayout(1));
        Label helperLabel = new Label(helperText);
        helperLabel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.BEGINNING,
                GridLayout.Alignment.END,
                true,
                false));
        helperLabel.setForegroundColor(TextColor.Factory.fromString(conf.getTheme().getHighlightedFg()));

        // --------------------------------------------------
        // Keyboards events
        // --------------------------------------------------
        addWindowListener(new HomeListener());

        // --------------------------------------------------
        // Add components
        // --------------------------------------------------

        masterPanel.addComponent(layoutPanel);
        masterPanel.addComponent(helperLabelPanel);

        layoutPanel.addComponent(leftPanelBordered);

        helperLabelPanel.addComponent(helperLabel);

        leftPanel.addComponent(tableHeaderPanel);
        leftPanel.addComponent(tableSplitter);
        leftPanel.addComponent(customerTable);

        setComponent(masterPanel);
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

    private class DisabledTableHeaderRenderer implements TableHeaderRenderer<String> {

        @Override
        public TerminalSize getPreferredSize(Table<String> table, String label, int columnIndex) {
            // Return a zero size to remove the empty space above
            if (label == null) {
                return TerminalSize.ZERO;
            }
            return new TerminalSize(TerminalTextUtils.getColumnWidth(label), 0);
        }

        @Override
        public void drawHeader(Table<String> table, String label, int index, TextGUIGraphics textGUIGraphics) {
            // Empty implementation to remove the header
        }
    }

}
