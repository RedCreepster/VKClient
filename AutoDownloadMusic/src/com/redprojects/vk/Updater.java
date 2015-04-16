package com.redprojects.vk;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.GlobalException;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Audio;
import com.redprojects.vk.autodownloadmusic.utils.PlayListManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Updater implements Runnable {

    final VKAPI vkapi;
    final File path;
    final String playlistName;
    final Audio audio;
    final PlayListManager.Types type;
    final int updateInterval;

    HashMap<Integer, URL> oldItemsMap = null;
    JSONArray jsonItems;
    HashMap<Integer, URL> itemsMap;

    public Updater(VKAPI vkapi, File path, String playlistName, PlayListManager.Types type, int updateInterval) {
        this.vkapi = vkapi;
        this.path = path;
        this.playlistName = playlistName;
        this.type = type;
        this.updateInterval = updateInterval;
        if (ProgramArguments.getInstance().get("getLoginUrl").getValue().equals(true) || ProgramArguments.getInstance().get("token").getValue() == null) {
            Log.console(this, vkapi.getLoginUrl());
            System.exit(0);
        }
        audio = new Audio(vkapi);
    }

    public static HashMap<Integer, URL> convertJsonItemsToMap(JSONArray items) throws MalformedURLException {
        HashMap<Integer, URL> map = new HashMap<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            map.put(item.getInt("id"), new URL(item.getString("url")));
        }
        return map;
    }

    private boolean deleteOldFiles(HashMap<Integer, URL> items, File path) {
        File[] files = path.listFiles();

        if (files != null)
            for (File p : files) {
                String id = p.getName();
                id = id.substring(0, id.indexOf("."));
                if (!items.keySet().contains(Integer.valueOf(id)))
                    if (!p.delete())
                        return false;
                    else
                        Log.console(this, "Delete old file " + p.getName());
            }
        return true;
    }

    public void updateLibrary(HashMap<Integer, URL> items, File path) throws IOException {
        for (int id : items.keySet()) {
            File file = new File(path.getAbsolutePath() + File.separator + id + ".mp3");
            URL url = items.get(id);
            if (!file.exists() || (file.exists() && file.length() != url.openConnection().getContentLength())) {
                Log.console("AutoDownloadMusic", "Download " + file);
                BufferedInputStream bis = new BufferedInputStream(url.openStream());
                Utils.copyInputStreamToFile(bis, file);
                bis.close();
                Log.console("AutoDownloadMusic", "Download " + file + " finished");
            }
        }
    }

    @Override
    public void run() {
        try {
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    JSONObject response = audio.get(vkapi.getUserId(), 0, new int[0], 0, 0, 0);
                    jsonItems = response.getJSONObject("response").getJSONArray("items");
                    itemsMap = convertJsonItemsToMap(jsonItems);
                    if (!itemsMap.equals(oldItemsMap)) {
                        Log.console("AutoDownloadMusic", "Update");
                        oldItemsMap = itemsMap;
                        updateLibrary(itemsMap, path);
                        deleteOldFiles(itemsMap, path);
                        Utils.copyStringToFile(
                                PlayListManager.getPlayList(jsonItems, path.getAbsolutePath() + File.separator, type),
                                new File(path + File.separator + ".." + File.separator + playlistName + "." + type.name().toLowerCase())
                        );
                        Log.console("AutoDownloadMusic", "Update finished");
                    }
                    Thread.sleep(updateInterval);
                }
            } catch (VKAPIException e) {
                if (e instanceof GlobalException && (e.getCode() == 6 || e.getCode() == 9 || e.getCode() == 10)) {
                    Log.console(this, e.getMessage() + "\nПробуем ещё раз через 5 секунд.");
                    Thread.sleep(5000);
                } else
                    e.printStackTrace();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
