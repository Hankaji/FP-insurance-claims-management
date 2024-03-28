/** 
* @author <Hoang Thai Phuc - s3978081> 
*/ 
package com.hankaji.icm;

import java.io.IOException;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.hankaji.icm.app.Welcome;
import com.hankaji.icm.config.Config;

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
        Config conf = Config.load();

        try {
            // Create screen
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();

            // Instantiate the MultiWindow Text GUI
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            // Create default Theme
            Theme defaultTheme = new SimpleTheme(ANSI.DEFAULT, TextColor.Factory.fromString("#24283b"));
            Theme defautBackgroundPane = new SimpleTheme(ANSI.DEFAULT, TextColor.Factory.fromString("#24283b"));

            textGUI.setTheme(defaultTheme);
            textGUI.getBackgroundPane().setTheme(defautBackgroundPane);

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
