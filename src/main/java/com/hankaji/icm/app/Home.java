package com.hankaji.icm.app;

import java.io.IOException;

import com.googlecode.lanterna.terminal.Terminal;

public class Home {

    private Terminal terminal;
    private int[] position;
    private int[] size;
    private boolean visible = true;

    public Home(Terminal terminal, int[] position, int[] size) {
        this.terminal = terminal;
        this.position = position;
        this.size = size;
    }

    public void draw() throws IOException {
        if (visible) {
            for (int i = position[0]; i < position[0] + size[0]; i++) {
                for (int j = position[1]; j < position[1] + size[1]; j++) {
                    terminal.setCursorPosition(i, j);
                    terminal.putCharacter(' ');
                }
            }
        }
    }
}
