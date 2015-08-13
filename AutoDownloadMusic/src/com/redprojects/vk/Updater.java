package com.redprojects.vk;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.GlobalException;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Audio;
import com.redprojects.vk.autodownloadmusic.utils.PlayListManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Updater implements Runnable {

    private final VKAPI vkapi;
    private final File path;
    private final String playlistName;
    private final Audio audio;
    private final PlayListManager.Types type;
    private final int updateInterval;

    private com.redprojects.vk.api.objects.Audio[] oldAudios = null;

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

    private boolean deleteOldFiles(com.redprojects.vk.api.objects.Audio[] audios, File path) {
        File[] files = path.listFiles();
        if (files == null || files.length == 0)
            return false;

        ArrayList<File> fileArrayList = new ArrayList<>(Arrays.asList(files));

        for (com.redprojects.vk.api.objects.Audio audio : audios) {
            File file = new File(path.getAbsolutePath() + File.separator + audio.getFileName() + ".mp3");
            if (fileArrayList.contains(file))
                fileArrayList.remove(file);
        }

        for (File file : fileArrayList)
            if (!file.delete())
                return false;
            else
                Log.console(this, "Delete old file " + file.getName());
        return true;
    }

    public void updateLibrary(com.redprojects.vk.api.objects.Audio[] audios, File path) throws IOException {
        for (com.redprojects.vk.api.objects.Audio audio : audios) {
            File file = new File(path.getAbsolutePath() + File.separator + audio.getFileName() + ".mp3");
            URL url = new URL(audio.getUrl());
            if (!file.exists() || (file.exists() && file.length() != url.openConnection().getContentLength())) {
                Log.console(this, "Download " + audio.getFullName());
                BufferedInputStream bis = new BufferedInputStream(url.openStream());
                Utils.copyInputStreamToFile(bis, file);
                bis.close();
                Log.console(this, "Download " + audio.getFullName() + " finished");
            }
        }
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                try {
                    com.redprojects.vk.api.objects.Audio[] audios = audio.get(vkapi.getUserId(), 0, new int[0], 0, 0, 0);
                    if (!Arrays.equals(audios, oldAudios)) {
                        Log.console(this, "Update");
                        oldAudios = audios;
                        updateLibrary(audios, path);
                        deleteOldFiles(audios, path);
                        Utils.copyStringToFile(
                                PlayListManager.getPlayList(audios, path.getAbsolutePath() + File.separator, type),
                                new File(path + File.separator + ".." + File.separator + playlistName + "." + type.name().toLowerCase())
                        );
                        Log.console(this, "Update finished");
                    }
                    Thread.sleep(updateInterval);
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
}
