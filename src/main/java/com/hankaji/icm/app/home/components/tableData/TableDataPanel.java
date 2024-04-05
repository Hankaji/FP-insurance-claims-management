package com.hankaji.icm.app.home.components.tableData;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

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

/**
 * A panel that displays data in a table format.
 *
 * @param <T> the type of data to be displayed in the table
 */
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
     * Constructs a TableDataPanel with the specified table titles, row mapper function, database, and update callbacks.
     *
     * @param tableTitles       the list of table titles
     * @param rowMapper         the function that maps a T object to a string array representing a row in the table
     * @param db                the database for CRUD operations
     * @param updateHelperText  the callback function to update the helper text
     * @param updateInfoBox     the callback function to update the info box
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

        // Table header
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

    /**
     * Called when the panel is added to a container.
     *
     * @param container the container to which the panel is added
     */
    @Override
    public synchronized void onAdded(Container container) {
        super.onAdded(container);

        update();
        updateHelperText.accept(useHelperText());
        CompletableFuture.runAsync(() -> {
            table.takeFocus();
        });
    }

    /**
     * Returns the helper text to be displayed.
     *
     * @return the map of helper text
     */
    protected abstract Map<String, String> useHelperText();

    /**
     * Returns the information of the selected object.
     *
     * @return the information of the selected object
     */
    protected String getObjectInfo() {
        return db.getById(table.getTableModel().getRow(table.getSelectedRow()).get(idColumn)).get().showInfoBox();
    };

    /**
     * Updates the table with the latest data from the database.
     */
    protected void update() {
        table.getTableModel().clear();
        for (T dep : db.getAll()) {
            String[] truncatedCells = truncateCell(rowMapper.apply(dep));
            table.getTableModel().addRow(truncatedCells);
        }
    }

    /**
     * Truncates the cell if it is longer than the header.
     *
     * @param cells the array of cells
     * @return the array of truncated cells
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

    /**
     * Returns the panel with a border.
     *
     * @return the panel with a border
     */
    @Override
    public synchronized Border withBorder() {
        return super.withBorder(Borders.doubleLineBevel("Table Data"));
    }

    /**
     * Returns the table component.
     *
     * @return the table component
     */
    public Table<String> getTable() {
        return table;
    }

    /**
     * Handles the input key stroke.
     *
     * @param key the input key stroke
     * @return {@code true} if the key stroke is handled, {@code false} otherwise
     */
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

    /**
     * Called when the add key is pressed.
     */
    protected abstract void onAddKeyPressed();

    /**
     * Called when the edit key is pressed.
     */
    protected abstract void onEditKeyPressed();

    /**
     * Deletes the selected row from the table and the database.
     * This method uses the idColumn protected field to get the id of the object to delete.
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
