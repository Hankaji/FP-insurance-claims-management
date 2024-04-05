package com.hankaji.icm.lib;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableHeaderRenderer;

/**
 * This class is an implementation of the TableHeaderRenderer interface that renders disabled table headers.
 * It removes the empty space above the header and does not draw the header itself.
 */
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
