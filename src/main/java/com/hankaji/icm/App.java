/** 
* @author <Hoang Thai Phuc - s3978081> 
*/ 
package com.hankaji.icm;

import java.io.IOException;
import java.util.Random;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
// import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;
import com.hankaji.icm.config.Config;

/**
 * The head Application program of the project
 *
 */
public class App  {
    public static void main( String[] args ) {
        // TApp tApp = new TApp();
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;

        Config conf = Config.loadConfig();

        try {
            terminal = defaultTerminalFactory.createTerminal();
            // terminal.enterPrivateMode();
            // terminal.clearScreen();
            // terminal.setCursorVisible(false);

            // ActionListBox actionListBox = new ActionListBox();
            // actionListBox.addItem("Claim 1", () -> {});
            // actionListBox.addItem("Claim 2", () -> {});
            // actionListBox.addItem("Claim 3", () -> {});
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            screen.setCursorPosition(null);

            drawRec(screen, screen.getTerminalSize().getColumns(), screen.getTerminalSize().getRows() - 2, "Claim List");

            TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.Factory.fromString(conf.getFg()));
            textGraphics.putString(new TerminalPosition(1, screen.getTerminalSize().getRows() - 1), "<Arrow up> / <Arrow down>: Move up / Move down; <Enter>: Select; <ESC>: Exit");

            screen.refresh();

            // terminal.addResizeListener(new TerminalResizeListener() {
            //     @Override
            //     public void onResized(Terminal term, TerminalSize newSize) {
            //         try {
            //             drawRec(screen);
            //             screen.refresh();
            //         } catch (IOException e) {
            //             e.printStackTrace();
            //         }
            //     }
            // });

            terminal.readInput();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (terminal != null) {
                try {
                    terminal.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void drawRec(Screen screen, int width, int height, String boxName) throws IOException {
        // Calculate box positions
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
}
