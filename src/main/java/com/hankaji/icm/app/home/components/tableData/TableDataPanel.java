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
import com.googlecode.lanterna.input.KeyStroke;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.lib.DisabledTableHeaderRenderer;
import com.hankaji.icm.lib.GsonSerializable;
import com.hankaji.icm.lib.HasBorder;
import com.hankaji.icm.lib.StringInfo;
import com.hankaji.icm.system.CRUD;

import static com.hankaji.icm.lib.Utils.LayoutUtils.*;

public abstract class TableDataPanel<T extends GsonSerializable & StringInfo> extends Panel implements HasBorder {

    // Fields
    private Config conf = Config.getInstance();

    private final CRUD<T> db;

    Function<T, String[]> rowMapper;

    Consumer<Map<String, String>> updateHelperText;

    protected int idColumn = 0;

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
    public <DB extends CRUD<T>> TableDataPanel(
            Collection<String> tableTitles,
            Function<T, String[]> rowMapper,
            DB db,
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

        table.setSelectAction(() -> {
            try {
                updateInfoBox.accept(getObjectInfo());
            } catch (Exception e) {
                updateInfoBox.accept("No object selected");
            }
        });

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
        return db.getById(table.getTableModel().getRow(table.getSelectedRow()).get(idColumn)).get().showInfoBox();
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

    @Override
    public boolean handleInput(KeyStroke key) {
        switch (key.getCharacter()) {
            case 'a':
                onAddKeyPressed();
                update();
                return true;
            case 'e':
                onEditKeyPressed();
                update();
                return true;
            case 'd':
                onDeleteKeyPressed();
                return true;
            case null:
                break;
            default:
                break;
        }
        return super.handleInput(key);
    }

    protected abstract void onAddKeyPressed();

    protected abstract void onEditKeyPressed();

    /**
     * Delete the selected row from the table and the database.
     * This method use idColumn protected field to get the id of the object to
     * delete.
     * Change the idColumn to the column index of the id if needed.
     */
    protected void onDeleteKeyPressed() {
        // Get id and delete the row
        String id = table.getTableModel().getRow(table.getSelectedRow()).get(idColumn);
        table.getTableModel().removeRow(table.getSelectedRow());

        T currObj = db.getById(id.trim()).get();
        db.delete(currObj);
    };

}
