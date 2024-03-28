package com.hankaji.icm.app;

import com.hankaji.icm.lib.Utils;

public abstract class NoDecorationWindow extends DefaultWindow {
    public NoDecorationWindow() {
        super();
        setHints(Utils.extendsCollection(getHints(), Hint.NO_DECORATIONS));
    }
}
