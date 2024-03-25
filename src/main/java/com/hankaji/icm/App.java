/** 
* @author <Hoang Thai Phuc - s3978081> 
*/ 
package com.hankaji.icm;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.screen.Screen;
// import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.hankaji.icm.app.Welcome;
import com.hankaji.icm.config.Config;
// import com.googlecode.lanterna.terminal.TerminalResizeListener;
import com.hankaji.icm.config.ConfigLoader;

/**
 * The head Application program of the project
 *
 */
public class App  {
    public static void main( String[] args ) {
        // TApp tApp = new TApp();
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        @SuppressWarnings("unused")
        Config conf = ConfigLoader.load();

        try {
            // Create screen
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();

            // Instantiate the MultiWindow Text GUI
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            // Create default Theme
            Theme defaultTheme = new SimpleTheme(ANSI.DEFAULT, ANSI.DEFAULT, SGR.BOLD);

            textGUI.setTheme(defaultTheme);
            textGUI.getBackgroundPane().setTheme(defaultTheme);

            // Draw a box
            // Window window = new BasicWindow("Test Window");
            // window.setHints(Arrays.asList(Hint.FIXED_POSITION, Hint.FIXED_SIZE));
            // window.setFixedSize(screen.getTerminalSize().withRelative(-2, -2));
            // window.setPosition(new TerminalPosition(0, 0));

            textGUI.addWindowAndWait(new Welcome());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (screen != null) {
                try {
                    screen.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
