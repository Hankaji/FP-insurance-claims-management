package com.hankaji.icm.app.home.components;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.util.Map;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.hankaji.icm.config.Config;

/**
 * A custom panel component that displays helper text information.
 * It extends the Lanterna Panel class.
 */
public class HelperText extends Panel {

    // Fields
    Map<String, String> helperText;

    Config conf = Config.getInstance();

    /**
     * Constructs a new HelperText instance with an empty helperText map.
     */
    public HelperText() {
        this(Map.of());
    }

    /**
     * Constructs a new HelperText instance with the specified helperText map.
     * 
     * @param helperText the map containing the helper text information
     */
    public HelperText(Map<String, String> helperText) {
        super(new LinearLayout(Direction.HORIZONTAL));

        this.helperText = helperText;
        updateHelperText(helperText);
    }

    /**
     * Returns the current helperText map.
     * 
     * @return the map containing the helper text information
     */
    public Map<String, String> getHelperText() {
        return helperText;
    }

    /**
     * Updates the helperText map and refreshes the displayed helper text components.
     * 
     * @param helperText the new map containing the updated helper text information
     */
    public void updateHelperText(Map<String, String> helperText) {
        this.helperText = helperText;
        removeAllComponents();
        int idx = 0;
        for (Map.Entry<String, String> entry : helperText.entrySet()) {
            Label inforLabel = new Label(idx == 0 ? 
                (entry.getKey() + ":") 
                  : 
                ("| " + entry.getKey() + ":"));
            Label keyLabel = new Label(entry.getValue());
            keyLabel.setForegroundColor(TextColor.Factory.fromString(conf.getTheme().getHighlightedFg()));
            
            addComponent(inforLabel);
            addComponent(keyLabel);

            idx++;
        }
        invalidate();
    }

}
