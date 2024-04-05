package com.hankaji.icm.lib;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import com.googlecode.lanterna.gui2.Border;

/**
 * Interface for classes that can have a border.
 * This return the object with border already initialized.
 */
public interface HasBorder {
    public abstract Border withBorder();
}
