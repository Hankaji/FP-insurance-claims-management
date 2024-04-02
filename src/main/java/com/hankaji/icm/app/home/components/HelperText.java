package com.hankaji.icm.app.home.components;

import java.util.Map;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.hankaji.icm.config.Config;

public class HelperText extends Panel {

    // Fields
    Map<String, String> helperText;

    Config conf = Config.getInstance();

    public HelperText() {
        this(Map.of());
    }

    public HelperText(Map<String, String> helperText) {
        super(new LinearLayout(Direction.HORIZONTAL));

        this.helperText = helperText;
        updateHelperText(helperText);
    }

    public Map<String, String> getHelperText() {
        return helperText;
    }

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
