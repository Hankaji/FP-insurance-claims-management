package com.hankaji.icm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.io.IOException;

import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
// import com.hankaji.icm.app.addNewForm.AddDependent;
import com.hankaji.icm.app.home.Home;
import com.hankaji.icm.config.Config;
import com.hankaji.icm.services.ClaimManager;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

import static com.hankaji.icm.lib.Utils.useHex;

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

            // ------------------------------ Theme ------------------------------
            // load theme from config
            Config.Theme theme = conf.getTheme();

            // Create default Theme
            SimpleTheme defaultTheme = new SimpleTheme(useHex(theme.getPriFg()), useHex(theme.getPriBg()));

            SimpleTheme.Definition defaultThemeDef = defaultTheme.getDefaultDefinition();
            defaultThemeDef.setSelected(useHex(theme.getSelectedFg()), useHex(theme.getSelectedBg()));
            defaultThemeDef.setActive(useHex(theme.getActiveFg()), useHex(theme.getActiveBg()));

            Theme defautBackgroundPane = new SimpleTheme(ANSI.DEFAULT, useHex(theme.getPriBg()));


            textGUI.setTheme(defaultTheme);
            textGUI.getBackgroundPane().setTheme(defautBackgroundPane);

            // textGUI.addWindowAndWait(new AddDependent());
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

            // Save data before exit
            DependentManager.getInstance().saveData();
            PolicyHolderManager.getInstance().saveData();
            InsuranceCardManager.getInstance().saveData();
            ClaimManager.getInstance().saveData();
        }

    }
}
