/** 
* @author <Hoang Thai Phuc - s3978081> 
*/ 
package com.hankaji.icm;

import java.io.IOException;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

/**
 * The head Application program of the project
 *
 */
public class App  {
    public static void main( String[] args ) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);

            final TextGraphics textGraphics = terminal.newTextGraphics();

            // terminal.addResizeListener(new TerminalResizeListener() {
            //     @Override
            //     public void onResized(Terminal terminal, TerminalSize newSize) {
            //         // Be careful here though, this is likely running on a separate thread. Lanterna is threadsafe in 
            //         // a best-effort way so while it shouldn't blow up if you call terminal methods on multiple threads, 
            //         // it might have unexpected behavior if you don't do any external synchronization
            //         try {
            //             terminal.clearScreen();

            //             if (newSize.getColumns() < 50 || newSize.getRows() < 30) {
            //                 textGraphics.drawLine(0, 0, newSize.getColumns() - 1, newSize.getRows() - 1, ' ');
            //                 textGraphics.putString(
            //                     (newSize.getColumns() - 1 - "Terminal Size too small ".length() - newSize.toString().length()) / 2,
            //                     newSize.getRows() / 2 - 1,
            //                     "Terminal Size too small ", SGR.BOLD);
            //                 textGraphics.putString(((newSize.getColumns() - 1 - "Terminal Size too small ".length()) / 2) + "Terminal Size too small ".length(),
            //                     newSize.getRows() / 2 - 1, newSize.toString());
            //             }
                    
            //             terminal.flush();
            //         }
            //         catch(Exception e) {
            //             // Not much we can do here
            //             throw new RuntimeException(e);
            //         }
            //     }
            // });

            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            textGraphics.putString(2, 1, "ICM - Press ESC to exit", SGR.BOLD);

            textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
            textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
            textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD);
            textGraphics.putString(5 + "Terminal Size: ".length(), 3, terminal.getTerminalSize().toString());

            KeyStroke key = terminal.readInput();
            if (key != null && key.getKeyType() == KeyType.Escape) {
                return;
            }

            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            textGraphics.putString(5, 10, "You pressed: " + key.getCharacter(), SGR.BOLD);

            terminal.flush();

            Thread.sleep(10000);
            
        } catch(Exception  e) {
            e.printStackTrace();
        } finally {
            if(terminal != null) {
                try {
                    terminal.close();
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
