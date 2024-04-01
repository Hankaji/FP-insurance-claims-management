package com.hankaji.icm.app;

import static com.hankaji.icm.lib.Utils.extendsCollection;

import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class PopupWindow extends DefaultWindow {

    public PopupWindow(String title) {
        super(title);
        setHints(extendsCollection(getHints(), Hint.CENTERED, Hint.MODAL));

        addWindowListener(new PopupListener());
    }

    private class PopupListener implements WindowListener {

        @Override
        public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
            if (keyStroke.getKeyType() != null && keyStroke.getKeyType() == KeyType.Escape) {
                close();
            }
            if (keyStroke.getCharacter() != null && keyStroke.getCharacter() == 'q') {
                close();
            }
        }

        @Override
        public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {}

        @Override
        public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {}

        @Override
        public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {}
        
    } 
    
}
