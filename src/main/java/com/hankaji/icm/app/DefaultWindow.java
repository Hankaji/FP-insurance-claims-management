package com.hankaji.icm.app;

import java.util.Arrays;
import java.util.HashSet;

import com.googlecode.lanterna.gui2.BasicWindow;

public abstract class DefaultWindow extends BasicWindow {
    public DefaultWindow() {
        super();
        this.setHints(new HashSet<>(Arrays.asList(Hint.NO_POST_RENDERING)));
    }
}
