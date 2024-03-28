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
import com.hankaji.icm.app.Home;
import com.hankaji.icm.config.Config;

/**
 * The head Application program of the project
 *
 */
public class App  {
    public static void main( String[] args ) {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        Config conf = Config.load();

        try {
            // Create screen
            screen = defaultTerminalFactory.createScreen();
            screen.startScreen();

            // Instantiate the MultiWindow Text GUI
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

            // Create default Theme
            SimpleTheme defaultTheme = new SimpleTheme(ANSI.DEFAULT, TextColor.Factory.fromString(conf.getTheme().getPriBg()));
            SimpleTheme.Definition defaultThemeDef = defaultTheme.getDefaultDefinition();
            defaultThemeDef.setSelected(ANSI.BLACK, ANSI.CYAN);
            // defaultThemeDef.setActive(ANSI.BLACK, ANSI.GREEN);

            Theme defautBackgroundPane = new SimpleTheme(ANSI.DEFAULT, TextColor.Factory.fromString(conf.getTheme().getPriBg()));


            textGUI.setTheme(defaultTheme);
            textGUI.getBackgroundPane().setTheme(defautBackgroundPane);

            // textGUI.addWindowAndWait(new Welcome());
            textGUI.addWindowAndWait(new Home());

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
