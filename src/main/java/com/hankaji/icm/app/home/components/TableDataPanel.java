package com.hankaji.icm.app.home.components;

import java.util.Collection;
import java.util.function.Function;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;
import com.googlecode.lanterna.gui2.table.Table;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.lib.DisabledTableHeaderRenderer;

import static com.hankaji.icm.lib.Utils.LayoutUtils.*;

public class TableDataPanel<T> extends Panel {

    private Config conf = Config.getInstance();

    /**
     * @param tableTitles List of table titles
     * @param rowMapper  Function which take a PocicyHolder and return a String array, which will be used as a row in the table.
     * Array returned must have the same length as the tableTitles
     * @param data List of T objects
     */
    public TableDataPanel(Collection<String> tableTitles, Function<T, String[]> rowMapper, Collection<T> data) {
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
        Table<String> customerTable = new Table<String>(
                tableTitles.toArray(new String[0]));
        for (T dep : data) {
            customerTable.getTableModel().addRow(rowMapper.apply(dep));
        }

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
}
