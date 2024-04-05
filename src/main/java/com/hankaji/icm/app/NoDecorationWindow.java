package com.hankaji.icm.app;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import com.hankaji.icm.lib.Utils;

/**
 * A window with no decorations.
 */
public abstract class NoDecorationWindow extends DefaultWindow {

    /**
     * Constructs a new NoDecorationWindow object with the given title.
     * This window has no decorations (no border).
     * 
     * @param title The title of the window.
     */
    public NoDecorationWindow(String title) {
        super(title);
        setHints(Utils.extendsCollection(getHints(), Hint.NO_DECORATIONS));
    }
}
