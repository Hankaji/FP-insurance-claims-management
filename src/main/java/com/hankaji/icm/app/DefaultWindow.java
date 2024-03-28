package com.hankaji.icm.app;

import java.util.Arrays;

import com.googlecode.lanterna.gui2.BasicWindow;

public abstract class DefaultWindow extends BasicWindow {
    public DefaultWindow(String title) {
        super(title);
        this.setHints(Arrays.asList(Hint.NO_POST_RENDERING));
    }
}
