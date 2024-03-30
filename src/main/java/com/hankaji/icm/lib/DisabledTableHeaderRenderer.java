package com.hankaji.icm.lib;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableHeaderRenderer;

public class DisabledTableHeaderRenderer implements TableHeaderRenderer<String> {

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
