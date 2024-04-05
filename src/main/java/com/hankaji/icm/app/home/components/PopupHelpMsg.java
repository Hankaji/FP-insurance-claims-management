package com.hankaji.icm.app.home.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Label;
import com.hankaji.icm.app.PopupWindow;

public class PopupHelpMsg extends PopupWindow {

    public PopupHelpMsg() {
        super("Helper");

        // Calculate the size of the window, width = 50% of the terminal width, height = amount of text lines
        int width = 50;
        TerminalSize popupSize = new TerminalSize(width, WelcomePanel.helpMessage.length() / width + 1);
        // Set the help message
        setComponent(new Label(WelcomePanel.helpMessage).setPreferredSize(popupSize));

    }
    
}
