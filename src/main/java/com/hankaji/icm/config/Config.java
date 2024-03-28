package com.hankaji.icm.config;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Config {

    // Singleton object
    private transient static Config INSTANCE = null;

    // Config fields
    private Theme theme;


    // Config loader and saver
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
        Config.INSTANCE = config;

        return Config.INSTANCE;
    }

    public static void save() {
        // save config from file
    }

    // --------------------------------------------------
    // Getters and Setters
    // --------------------------------------------------
    // Singleton getter
    public static Config getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = Config.load();
        }

        return INSTANCE;
    }

    // --------------------------------------------------
    // Fields and getters and setters

    public Theme getTheme() {
        return theme;
    }
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    // --------------------------------------------------
    // Inner class for for nested fields
    // --------------------------------------------------
    public class Theme {
        @SerializedName(value = "pri_bg")
        private String priBg;

        @SerializedName(value = "pri_fg")
        private String priFg;

        @SerializedName(value = "active_fg")
        private String activeFg;

        public String getPriBg() {
            return priBg;
        }

        public void setPriBg(String priBg) {
            this.priBg = priBg;
        }

        public String getPriFg() {
            return priFg;
        }

        public void setPriFg(String priFg) {
            this.priFg = priFg;
        }

        public String getActiveFg() {
            return activeFg;
        }

        public void setActiveFg(String activeFg) {
            this.activeFg = activeFg;
        }

        @Override
        public String toString() {
            return "Theme [priBg=" + priBg + ", priFg=" + priFg + ", activeFg=" + activeFg + "]";
        }
        
    }

}

final class DefaultConfig {
    public static final String config = "{\n" + //
                "    \"theme\": {\n" + //
                "        \"fg\": \"#7aa2f7\"\n" + //
                "    }\n" + //
                "}";
}
