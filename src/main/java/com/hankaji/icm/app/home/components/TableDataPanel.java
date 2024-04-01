package com.hankaji.icm.app.home.components;

import java.util.Collection;
import java.util.function.Function;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Border;
import com.googlecode.lanterna.gui2.Borders;
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

public class TableDataPanel<T> extends Panel implements TableData, HasBorder {

    // Fields
    private Config conf = Config.getInstance();

    private final DataManager<T> db;

    Function<T, String[]> rowMapper;

    // Components
    private Table<String> customerTable;

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
            DataManager<T> db) {
        super(new GridLayout(1));

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
        this.customerTable = new Table<String>(tableTitles.toArray(new String[0]));
        update();

        customerTable.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.FILL,
                GridLayout.Alignment.FILL,
                true,
                true));

        customerTable.setTableHeaderRenderer(new DisabledTableHeaderRenderer());

        addComponent(tableHeaderPanel);
        addComponent(tableSplitter);
        addComponent(customerTable);
    }

    @Override
    public void update() {
        customerTable.getTableModel().clear();
        for (T dep : db.getAll()) {
            customerTable.getTableModel().addRow(rowMapper.apply(dep));
        }
    }

    @Override
    public synchronized Border withBorder() {
        return super.withBorder(Borders.doubleLineBevel("Table Data"));
    }

    public Table<String> getCustomerTable() {
        return customerTable;
    }

}
