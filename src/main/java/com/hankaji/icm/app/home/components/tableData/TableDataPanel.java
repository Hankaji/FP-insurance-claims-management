package com.hankaji.icm.app.home.components.tableData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Container;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.table.Table;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.lib.DisabledTableHeaderRenderer;
import com.hankaji.icm.lib.HasBorder;
import com.hankaji.icm.system.DataManager;

import static com.hankaji.icm.lib.Utils.LayoutUtils.*;

public abstract class TableDataPanel<T> extends Panel implements HasBorder {

    // Fields
    private Config conf = Config.getInstance();

    private final DataManager<T> db;

    Function<T, String[]> rowMapper;

    Consumer<Map<String, String>> updateHelperText;

    // Components
    protected Table<String> table;

    /**
     * @param tableTitles List of table titles
     * @param rowMapper   Function which take a PocicyHolder and return a String
     *                    array, which will be used as a row in the table.
     *                    Array returned must have the same length as the
     *                    tableTitles
     * @param data        List of T objects
     */
    public TableDataPanel(
            Collection<String> tableTitles,
            Function<T, String[]> rowMapper,
            DataManager<T> db,
            Consumer<Map<String, String>> updateHelperText,
            Consumer<String> updateInfoBox) {
        super(new GridLayout(1));

        this.updateHelperText = updateHelperText;

        setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

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
        this.db = db;
        this.rowMapper = rowMapper;
        this.table = new Table<String>(tableTitles.toArray(new String[0]));
        update();

        table.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        table.setTableHeaderRenderer(new DisabledTableHeaderRenderer());

        table.setSelectAction(() -> updateInfoBox.accept(getObjectInfo()));

        addComponent(tableHeaderPanel);
        addComponent(tableSplitter);
        addComponent(table);
    }

    @Override
    public synchronized void onAdded(Container container) {
        super.onAdded(container);
        
        update();
        updateHelperText.accept(useHelperText());
        CompletableFuture.runAsync(() -> {
            table.takeFocus();
        });
    }

    protected abstract Map<String, String> useHelperText();

    protected String getObjectInfo() {
        return "No info available.";
    };

    protected void update() {
        table.getTableModel().clear();
        for (T dep : db.getAll()) {
            String[] truncatedCells = truncateCell(rowMapper.apply(dep));
            table.getTableModel().addRow(truncatedCells);
        }
    }

    /**
     * Truncate cell if it is longer than the header.
     * 
     * @param cells Array of cells
     * @return Array of truncated cells
     */
    private String[] truncateCell(String[] cells) {
        List<String> cols = table.getTableModel().getColumnLabels();

        String[] truncatedCells = new String[cells.length];

        int idx = 0;
        for (String header : cols) {
            int headerLength = header.length();

            String cell = cells[idx];

            // If cell longer than header length, truncate it and add "..."
            if (headerLength > 3 && cell.length() > headerLength) {
                cell = cell.substring(0, headerLength - 3) + "...";
            }

            truncatedCells[idx] = cell;

            idx++;
        }

        return truncatedCells;
    }

    @Override
    public synchronized Border withBorder() {
        return super.withBorder(Borders.doubleLineBevel("Table Data"));
    }

    public Table<String> getTable() {
        return table;
    }

}
