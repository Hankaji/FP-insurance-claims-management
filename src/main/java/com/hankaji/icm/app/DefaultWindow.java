package com.hankaji.icm.app;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.Arrays;

import com.googlecode.lanterna.gui2.BasicWindow;

/**
 * The DefaultWindow class is an abstract class that extends the BasicWindow class.
 * It is used to create a window with no post rendering.
 */
public abstract class DefaultWindow extends BasicWindow {

    /**
     * Constructs a new DefaultWindow object with the specified title.
     * This window has no post rendering (No shadow).
     * 
     * @param title The title of the window.
     */
    public DefaultWindow(String title) {
        super(title);
        this.setHints(Arrays.asList(Hint.NO_POST_RENDERING));
    }
}
