package com.redprojects.utils.config;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Settings extends HashMap<String, String> {
    protected static final Settings instance = new Settings();
    protected static final String configResource = "/default_config.json";

    protected String pathToConfigFile = null;
    private File configFile;

    protected Settings() {
        load();
        fix();
    }

    public static Settings getInstance() {
        return instance;
    }

    private void load() {
        JSONObject jsonConfig;

        if (pathToConfigFile == null)
            pathToConfigFile = Constants.getInstance().get("root") + "config.json";
        configFile = new File(pathToConfigFile);

        try {
            if (!configFile.exists()) {
                Utils.copyInputStreamToFile(this.getClass().getResourceAsStream(configResource), configFile);
            }
        } catch (IOException e) {
            Log.console(this, "Ошибка записи в файл " + pathToConfigFile);
            e.printStackTrace();
        }

        try {
            jsonConfig = new JSONObject(Utils.inputStreamToString(new BufferedInputStream(new FileInputStream(pathToConfigFile)), true));
        } catch (IOException e) {
            Log.console(this, "Ошибка чтения файла " + pathToConfigFile);
            e.printStackTrace();
            return;
        }
        this.putAll(jsonToMap(jsonConfig));
    }

    public void save() {
        if (pathToConfigFile == null || configFile == null)
            return;

        JSONObject jsonConfig = new JSONObject(this);
        try {
            FileOutputStream os = new FileOutputStream(configFile);
            os.write(jsonConfig.toString().getBytes());
            os.flush();
            os.close();
        } catch (IOException e) {
            Log.console(this, "Ошибка записи в файл " + pathToConfigFile);
            e.printStackTrace();
        }
    }

    private void fix() {
        try {
            JSONObject jsonDefaultConfig = new JSONObject(Utils.inputStreamToString(this.getClass().getResourceAsStream(configResource), true));
            //noinspection unchecked
            Iterator<String> iterator = jsonDefaultConfig.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                if (!this.containsKey(key)) {
                    this.put(key, jsonDefaultConfig.getString(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LinkedHashMap<String, String> jsonToMap(JSONObject jsonObject) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (Object key : jsonObject.keySet())
            result.put(key.toString(), jsonObject.getString(key.toString()));
        return result;
    }

    @Override
    public String put(String key, String value) {
        String result = super.put(key, value);
        save();
        return result;
    }
}
