package com.redprojects.starters;

import com.redprojects.bot.Messenger;
import com.redprojects.utils.Log;
import com.redprojects.utils.config.arguments.Argument;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.utils.config.arguments.converters.IntegerConverter;
import com.redprojects.utils.config.arguments.converters.ListConverter;
import com.redprojects.utils.config.arguments.converters.StringConverter;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;

public class MessageBot {

    public static void main(String[] args) throws InterruptedException, VKAPIException {
        Log.setFilePrefix(MessageBot.class.getSimpleName());

        ProgramArguments.getInstance().put("getLoginUrl", new Argument<>(true, "Get login url", "getLoginUrl", args));
        ProgramArguments.getInstance().put("token", new Argument<>(false, "Token", "token", args, new StringConverter()));
        ProgramArguments.getInstance().put("appId", new Argument<>(false, "Application id", "appId", args, new IntegerConverter()));
        ProgramArguments.getInstance().put("playerAllowedUsers", new Argument<>(false, "Player allowed user ids", "playerAllowedUsers", args, new ListConverter()));
        ProgramArguments.getInstance().put("player", new Argument<>(false, "Player", "player", args, new StringConverter(), "XBMC"));
        ProgramArguments.getInstance().put("xbmcServer", new Argument<>(false, "XBMC server address", "xbmcServer", args, new StringConverter()));
        ProgramArguments.getInstance().put("aimpPath", new Argument<>(false, "Path to AIMP.exe", "aimpPath", args, new StringConverter()));

        if (!ProgramArguments.getInstance().get("xbmcServer").isSet() && !ProgramArguments.getInstance().get("aimpPath").isSet()) {
            Log.console(ProgramArguments.getInstance().get("playerType"), "¬ы должны указать данные дл€ воспроизведени€.");
            System.exit(0);
        }

        VKAPI vkapi = new VKAPI(
                ProgramArguments.getInstance().get("token").getValue().toString(),
                0,
                (Integer) ProgramArguments.getInstance().get("appId").getValue(),
                new VKAPI.Scope[]{
                        VKAPI.Scope.messages,
                        VKAPI.Scope.audio,
                        VKAPI.Scope.photos,
                        VKAPI.Scope.docs,
                        VKAPI.Scope.offline}
        );

        Messenger messenger = new Messenger(vkapi);
        Thread thread = new Thread(messenger.startLongPoll());
        thread.setDaemon(false);
        thread.setName("Thread-Messenger");
        thread.start();
        thread.join();
    }
}
