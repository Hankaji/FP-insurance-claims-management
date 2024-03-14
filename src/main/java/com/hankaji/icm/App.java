/** 
* @author <Hoang Thai Phuc - s3978081> 
*/ 
package com.hankaji.icm;

import org.w3c.dom.Text;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

// import com.hankaji.icm.app.Home;
import com.hankaji.icm.lib.tui.TApp;

/**
 * The head Application program of the project
 *
 */
public class App  {
    public static void main( String[] args ) {
        // TApp tApp = new TApp();
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal = null;

        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);

            TerminalSize terminalSize = terminal.getTerminalSize();
            int width = terminalSize.getColumns();
            int height = terminalSize.getRows() - 2;

            final TextGraphics textGraphics = terminal.newTextGraphics();

            textGraphics.drawLine(new TerminalPosition(0, 0), new TerminalPosition(width, 0), '─');
            textGraphics.drawLine(new TerminalPosition(0, 0), new TerminalPosition(0, height), '│');
            textGraphics.drawLine(new TerminalPosition(width, height), new TerminalPosition(0, height), '─');
            textGraphics.drawLine(new TerminalPosition(width, height), new TerminalPosition(width, 0), '│');
            textGraphics.putString(0, 0, "┌");
            textGraphics.putString(width, 0, "┐");
            textGraphics.putString(0, height, "└");
            textGraphics.putString(width, height, "┘");

            textGraphics.putString(2, 0, "Claim list");

            textGraphics.setForegroundColor(new TextColor.RGB(122, 162, 247));
            textGraphics.putString(1, height + 1, "<Arrow up> / <Arrow down>: Move up / Move down; <Enter>: Select; <ESC>: Exit");
            terminal.readInput();
        } catch (Exception e) {
            e.printStackTrace();
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
}
