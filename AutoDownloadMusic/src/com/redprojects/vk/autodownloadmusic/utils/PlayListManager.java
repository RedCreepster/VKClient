package com.redprojects.vk.autodownloadmusic.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlayListManager {

    public enum Types {
        PLS, M3U
    }

    public static String getPlayList(JSONArray items, String path, Types type) {
        return type.equals(Types.M3U) ? getM3UPlayList(items, path) : getPlsPlayList(items, path);
    }

    public static String getPlsPlayList(JSONArray items, String path) {
        String pls = "[playlist]\n";
        int i;
        for (i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            pls += "File" + (i + 1) + "=" + path + item.getInt("id") + ".mp3\n" +
                    "Length" + (i + 1) + "=" + item.getInt("duration") + "\n" +
                    "Title" + (i + 1) + "=" + item.getString("artist") + " - " + item.getString("title") + "\n";
        }
        pls += "NumberOfEntries=" + (i + 1) + "\n" +
                "Version=2";
        return pls;
    }

    public static String getM3UPlayList(JSONArray items, String path) {
        String m3u = "#EXTM3U\n";
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            m3u += "#EXTINF:" + item.getInt("duration") + "," +
                    item.getString("artist") + " - " + item.getString("title") + "\n" +
                    path + item.getInt("id") + ".mp3\n";
        }
        return m3u;
    }
}
