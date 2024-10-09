package de.giovanni.luma.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {

    private static final String CONFIG_FILE = "luma.json";
    private Map<String, Object> configData;
    private Gson gson;

    public Configuration() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        loadConfig();
    }

    @SuppressWarnings("unchecked")
    private void loadConfig() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            configData = gson.fromJson(reader, HashMap.class);
            if (configData == null) {
                configData = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            configData = new HashMap<>();
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(configData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public <T> T getValue(String key, Class<T> clazz, T defaultValue) {
        Object value = configData.get(key);
        if (value != null) {
            return gson.fromJson(gson.toJson(value), clazz);
        }
        return defaultValue;
    }

    public <T> List<T> getList(String key, Type typeOfT, List<T> defaultValue) {
        Object value = configData.get(key);
        if (value != null) {
            return gson.fromJson(gson.toJson(value), typeOfT);
        }
        return defaultValue;
    }

    public void setValue(String key, Object value) {
        configData.put(key, value);
        saveConfig();
    }
}
