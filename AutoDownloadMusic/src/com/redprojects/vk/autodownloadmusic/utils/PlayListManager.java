package com.redprojects.vk.autodownloadmusic.utils;

import com.redprojects.vk.api.objects.Audio;

public class PlayListManager {

    public enum Types {
        PLS, M3U
    }

    public static String getPlayList(Audio[] audios, String path, Types type) {
        return type.equals(Types.M3U) ? getM3UPlayList(audios, path) : (type.equals(Types.PLS) ? getPlsPlayList(audios, path) : "");
    }

    public static String getPlsPlayList(Audio[] audios, String path) {
        String pls = "[playlist]\n";
        for (int i = 0; i < audios.length; i++)
            pls += "File" + (i + 1) + "=" + path + audios[i].getFileName() + ".mp3\n" +
                    "Length" + (i + 1) + "=" + audios[i].getDuration() + "\n" +
                    "Title" + (i + 1) + "=" + audios[i].getFullName() + "\n";

        pls += "NumberOfEntries=" + (audios.length - 1) + "\n" +
                "Version=2";
        return pls;
    }

    public static String getM3UPlayList(Audio[] audios, String path) {
        String m3u = "#EXTM3U\n";
        for (Audio audio : audios)
            m3u += "#EXTINF:" + audio.getDuration() + "," +
                    audio.getFullName() + "\n" +
                    path + audio.getFileName() + ".mp3\n";

        return m3u;
    }
}
