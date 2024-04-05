package com.hankaji.icm.app.home.components;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Label;
import com.hankaji.icm.app.PopupWindow;

/**
 * A popup window that displays the help message.
 */
public class PopupHelpMsg extends PopupWindow {

    /**
     * Constructs a new PopupHelpMsg object.
     * It sets the title of the popup window to "Helper".
     * It calculates the size of the window based on the width and height of the help message.
     * It sets the help message as the component of the popup window.
     */
    public PopupHelpMsg() {
        super("Helper");

        // Calculate the size of the window, width = 50% of the terminal width, height = amount of text lines
        int width = 50;
        TerminalSize popupSize = new TerminalSize(width, WelcomePanel.helpMessage.length() / width + 1);
        // Set the help message
        setComponent(new Label(WelcomePanel.helpMessage).setPreferredSize(popupSize));

    }
    
}
