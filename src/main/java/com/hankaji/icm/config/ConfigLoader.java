package com.hankaji.icm.config;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

public class ConfigLoader {

    private static Config INSTANCE = null;

    public static Config load() {
        // Find config.json file
        String json = "";
        try {
            json = FileUtils.readFileToString(new File("src/main/java/com/hankaji/icm/config/config.json"), "UTF-8");
        } catch (Exception e) {
            System.out.println("config.json not found, using default config");
            json = DefaultConfig.config;
        }

        // load config from file
        Gson gson = new Gson();
        Config config = gson.fromJson(json, Config.class);
        ConfigLoader.INSTANCE = config;

        return ConfigLoader.INSTANCE;
    }

    public static void save() {
        // save config from file
    }

    public static Config getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = ConfigLoader.load();
        }

        return INSTANCE;
    }

}

final class DefaultConfig {
    public static final String config = "{\n" + //
                "    \"theme\": {\n" + //
                "        \"fg\": \"#7aa2f7\"\n" + //
                "    } \n" + //
                "}";
}
