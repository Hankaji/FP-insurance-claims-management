package com.hankaji.icm.lib.tui.widgets;

import java.io.IOException;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class TUI {
    public static void drawRec(Screen screen, TerminalSize size, String boxName) throws IOException {
        // Calculate box positions
        int width = size.getColumns();
        int height = size.getRows();

        TerminalPosition boxTopLeft = new TerminalPosition(0, 0);
        TerminalPosition boxTopRight = boxTopLeft.withRelativeColumn(width - 1);
        TerminalPosition boxBottomLeft = boxTopLeft.withRelativeRow(height);
        TerminalPosition boxBottomRight = boxTopRight.withRelativeRow(height);

        final TextGraphics textGraphics = screen.newTextGraphics();

        // Draw box
        textGraphics.drawLine(boxTopLeft, boxTopRight, Symbols.SINGLE_LINE_HORIZONTAL);
        textGraphics.drawLine(boxTopRight, boxBottomRight, Symbols.SINGLE_LINE_VERTICAL);
        textGraphics.drawLine(boxBottomLeft, boxBottomRight, Symbols.SINGLE_LINE_HORIZONTAL);
        textGraphics.drawLine(boxTopLeft, boxBottomLeft, Symbols.SINGLE_LINE_VERTICAL);

        // Draw corners
        textGraphics.setCharacter(boxTopLeft, Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(boxTopRight, Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(boxBottomLeft, Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(boxBottomRight, Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);

        // Draw box name
        textGraphics.putString(boxTopLeft.withRelativeColumn(2), boxName);
    }

    public static void drawRec(Screen screen, String boxName) throws IOException {
        drawRec(screen, screen.getTerminalSize().withRelativeRows(-1), boxName);
    }
}
