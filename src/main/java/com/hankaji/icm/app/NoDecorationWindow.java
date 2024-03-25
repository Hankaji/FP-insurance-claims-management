package com.hankaji.icm.app;

import java.util.Arrays;

public abstract class NoDecorationWindow extends DefaultWindow {
    public NoDecorationWindow() {
        super();
        // this.getHints().addAll((Arrays.asList(Hint.NO_DECORATIONS)));
        this.setHints(Arrays.asList(Hint.NO_DECORATIONS));

    }
}
