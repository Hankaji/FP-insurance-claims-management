package com.hankaji.icm.lib.tui;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class TApp {

    private DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    Terminal terminal = null;

    public TApp() {
        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.enterPrivateMode();
            terminal.clearScreen();
            terminal.setCursorVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Terminal getTerminal() {
        return terminal;
    }

}
