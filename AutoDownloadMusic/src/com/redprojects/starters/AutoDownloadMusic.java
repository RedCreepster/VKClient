package com.redprojects.starters;

import com.redprojects.utils.Log;
import com.redprojects.utils.config.arguments.Argument;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.utils.config.arguments.converters.IntegerConverter;
import com.redprojects.utils.config.arguments.converters.StringConverter;
import com.redprojects.vk.Updater;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.autodownloadmusic.utils.PlayListManager;

import java.io.File;
import java.io.IOException;

public class AutoDownloadMusic {
    public static void main(String[] args) throws IOException, InterruptedException, VKAPIException {
        Log.setFilePrefix(AutoDownloadMusic.class.getSimpleName());

        ProgramArguments.getInstance().put("getLoginUrl", new Argument<>(true, "Get login url", "getLoginUrl", args));
        ProgramArguments.getInstance().put("appId", new Argument<>(false, "Application id", "appId", args, new IntegerConverter()));
        ProgramArguments.getInstance().put("token", new Argument<>(false, "Token", "token", args, new StringConverter()));
        ProgramArguments.getInstance().put("userId", new Argument<>(false, "User id", "userId", args, new IntegerConverter()));
        ProgramArguments.getInstance().put("path", new Argument<>(false, "Path to save", "path", args, new StringConverter()));
        ProgramArguments.getInstance().put("playlistName", new Argument<>(false, "Playlist name", "playlistName", args, new StringConverter()));
        ProgramArguments.getInstance().put("playlistType", new Argument<>(false, "Playlist type", "playlistType", args, new StringConverter()));
        ProgramArguments.getInstance().put("updateInterval", new Argument<>(false, "Update interval", "updateInterval", args, new IntegerConverter()));

        VKAPI vkapi = new VKAPI(
                ProgramArguments.getInstance().get("token").getValue().toString(),
                (Integer) ProgramArguments.getInstance().get("userId").getValue(),
                (Integer) ProgramArguments.getInstance().get("appId").getValue(),
                new VKAPI.Scope[]{VKAPI.Scope.audio, VKAPI.Scope.offline}
        );

        Updater updater = new Updater(
                vkapi,
                new File(ProgramArguments.getInstance().get("path").getValue().toString()),
                ProgramArguments.getInstance().get("playlistName").getValue().toString(),
                PlayListManager.Types.valueOf(ProgramArguments.getInstance().get("playlistType").getValue().toString()),
                (Integer) ProgramArguments.getInstance().get("updateInterval").getValue()
        );

        Thread thread = new Thread(updater);
        thread.start();
    }
}
