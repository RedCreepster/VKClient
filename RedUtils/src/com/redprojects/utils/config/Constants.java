package com.redprojects.utils.config;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;

public class Constants extends LinkedHashMap<String, String> {

    private static final Constants instance = new Constants();

    private Constants() {
        load();
    }

    public static Constants getInstance() {
        return instance;
    }

    private void load() {
        JSONObject jsonConfig;

        String configResource = "/constants.json";

        try {
            jsonConfig = new JSONObject(Utils.inputStreamToString(this.getClass().getResourceAsStream(configResource), true));
        } catch (IOException e) {
            Log.console(this, "Ошибка чтения ресурса " + configResource);
            e.printStackTrace();
            return;
        }
        this.putAll(jsonToMap(jsonConfig));

        for (String key : this.keySet())
            Log.console(this, key + " " + this.get(key));
    }

    private LinkedHashMap<String, String> jsonToMap(JSONObject jsonObject) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (Object key : jsonObject.keySet())
            result.put(key.toString(), jsonObject.getString(key.toString()));
        return result;
    }
}
