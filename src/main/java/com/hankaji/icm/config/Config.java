package com.hankaji.icm.config;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Configuration for the insurance claims management system.
 * The configuration includes a theme object that contains various color settings.
 * 
 */
public class Config {

    // Singleton object
    private transient static Config instance = null;

    // Config fields
    private Theme theme;

    /**
     * Loads the configuration from the config.json file.
     * If the file is not found, it uses the default configuration.
     * 
     * @return The loaded configuration object.
     */
    public static Config load() {
        // Find config.json file
        String json = "";
        try {
            json = FileUtils.readFileToString(new File("./config.json"), "UTF-8");
        } catch (Exception e) {
            System.out.println("config.json not found, using default config");
            json = DefaultConfig.config;
        }

        // load config from file
        Gson gson = new Gson();
        Config config = gson.fromJson(json, Config.class);
        Config.instance = config;

        return Config.instance;
    }

    /**
     * Saves the configuration to the config.json file.
     */
    public static void save() {
        // save config to file
    }

    // --------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------
    
    /**
     * Returns the singleton instance of the Config class.
     * If the instance is null, it loads the configuration from the file.
     * 
     * @return The singleton instance of the Config class.
     */
    public static Config getInstance() {
        if(instance == null) {
            instance = Config.load();
        }

        return instance;
    }

    // --------------------------------------------------
    // Fields and getters and setters

    /**
     * Returns the theme object that contains various color settings.
     * 
     * @return The theme object.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Sets the theme object that contains various color settings.
     * 
     * @param theme The theme object to set.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    // --------------------------------------------------
    // Inner class for nested fields
    // --------------------------------------------------
    
    /**
     * Theme settings for the insurance claims management system.
     * It contains various color settings for different UI elements.
     */
    public class Theme {
        @SerializedName(value = "pri_bg")
        private String priBg;

        @SerializedName(value = "pri_fg")
        private String priFg;

        @SerializedName(value = "active_bg")
        private String activeBg;

        @SerializedName(value = "active_fg")
        private String activeFg;

        @SerializedName(value = "selected_bg")
        private String selectedBg;

        @SerializedName(value = "selected_fg")
        private String selectedFg;

        @SerializedName(value = "highlighted_fg")
        private String highlightedFg;

        @SerializedName(value = "error_fg")
        private String errorFg;

        /**
         * Returns the primary background color.
         * 
         * @return The primary background color.
         */
        public String getPriBg() {
            return priBg;
        }

        /**
         * Sets the primary background color.
         * 
         * @param priBg The primary background color to set.
         */
        public void setPriBg(String priBg) {
            this.priBg = priBg;
        }

        /**
         * Returns the primary foreground color.
         * 
         * @return The primary foreground color.
         */
        public String getPriFg() {
            return priFg;
        }

        /**
         * Sets the primary foreground color.
         * 
         * @param priFg The primary foreground color to set.
         */
        public void setPriFg(String priFg) {
            this.priFg = priFg;
        }

        /**
         * Returns the active background color.
         * 
         * @return The active background color.
         */
        public String getActiveBg() {
            return activeBg;
        }

        /**
         * Sets the active background color.
         * 
         * @param activeBg The active background color to set.
         */
        public void setActiveBg(String activeBg) {
            this.activeBg = activeBg;
        }

        /**
         * Returns the active foreground color.
         * 
         * @return The active foreground color.
         */
        public String getActiveFg() {
            return activeFg;
        }

        /**
         * Sets the active foreground color.
         * 
         * @param activeFg The active foreground color to set.
         */
        public void setActiveFg(String activeFg) {
            this.activeFg = activeFg;
        }

        /**
         * Returns the selected background color.
         * 
         * @return The selected background color.
         */
        public String getSelectedBg() {
            return selectedBg;
        }

        /**
         * Sets the selected background color.
         * 
         * @param selectedBg The selected background color to set.
         */
        public void setSelectedBg(String selectedBg) {
            this.selectedBg = selectedBg;
        }

        /**
         * Returns the selected foreground color.
         * 
         * @return The selected foreground color.
         */
        public String getSelectedFg() {
            return selectedFg;
        }

        /**
         * Sets the selected foreground color.
         * 
         * @param selectedFg The selected foreground color to set.
         */
        public void setSelectedFg(String selectedFg) {
            this.selectedFg = selectedFg;
        }

        /**
         * Returns the highlighted foreground color.
         * 
         * @return The highlighted foreground color.
         */
        public String getHighlightedFg() {
            return highlightedFg;
        }

        /**
         * Sets the highlighted foreground color.
         * 
         * @param activeFg The highlighted foreground color to set.
         */
        public void setHighlightedFg(String activeFg) {
            this.highlightedFg = activeFg;
        }

        /**
         * Returns the error foreground color.
         * 
         * @return The error foreground color.
         */
        public String getErrorFg() {
            return errorFg;
        }

        /**
         * Sets the error foreground color.
         * 
         * @param errorFg The error foreground color to set.
         */
        public void setErrorFg(String errorFg) {
            this.errorFg = errorFg;
        }
    }
}

/**
 * The default configuration for the insurance claims management system.
 */
final class DefaultConfig {
    public static final String config = "{\n" + //
                "    \"theme\": {\n" + //
                "        \"pri_bg\": \"#24283b\",\n" + //
                "        \"pri_fg\": \"#a9b1d6\",\n" + //
                "\n" + //
                "        \"active_bg\": \"#24283b\",\n" + //
                "        \"active_fg\": \"#9ece6a\",\n" + //
                "\n" + //
                "        \"selected_bg\": \"#414868\",\n" + //
                "        \"selected_fg\": \"#a9b1d6\",\n" + //
                "        \n" + //
                "        \"highlighted_fg\": \"#7aa2f7\",\n" + //
                "\n" + //
                "        \"error_fg\": \"#f7768e\"\n" + //
                "    }\n" + //
                "}";
}
