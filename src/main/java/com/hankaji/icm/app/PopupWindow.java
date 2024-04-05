package com.hankaji.icm.app;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import static com.hankaji.icm.lib.Utils.extendsCollection;

import java.util.concurrent.atomic.AtomicBoolean;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

/**
 * A popup window that is centered and modal.
 */
public class PopupWindow extends DefaultWindow {

    /**
     * Constructs a new PopupWindow object with the specified title.
     * This window is centered and modal.
     * 
     * @param title The title of the window.
     */
    public PopupWindow(String title) {
        super(title);
        setHints(extendsCollection(getHints(), Hint.CENTERED, Hint.MODAL));

        addWindowListener(new PopupListener());
    }

    /**
     * Draws the window.
     * Aslo displays the hint "Exit window: q" at the bottom right corner.
     */
    @Override
    public void draw(TextGUIGraphics graphics) {
        super.draw(graphics);

        String hint = "Exit window: q";

        // Put string "Exit window: q" at the bottom right corner
        graphics.putString(
                graphics.getSize().getColumns() - hint.length() - 1,
                graphics.getSize().getRows() - 1,
                hint);
    }

    /**
     * Closes the window.
     */
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
