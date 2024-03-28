package com.hankaji.icm.app;

import com.hankaji.icm.lib.Utils;

public abstract class NoDecorationWindow extends DefaultWindow {
    public NoDecorationWindow(String title) {
        super(title);
        setHints(Utils.extendsCollection(getHints(), Hint.NO_DECORATIONS));
    }
}
