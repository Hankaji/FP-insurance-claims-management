package com.hankaji.icm.app;

import static com.hankaji.icm.lib.Utils.extendsCollection;

public class PopupWindow extends DefaultWindow {

    public PopupWindow(String title) {
        super(title);
        setHints(extendsCollection(getHints(), Hint.CENTERED, Hint.MODAL));
    }
    
}
