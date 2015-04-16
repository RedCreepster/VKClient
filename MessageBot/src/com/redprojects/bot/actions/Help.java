package com.redprojects.bot.actions;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import java.io.IOException;

public class Help extends IAction {
    public Help(JSONObject message, VKAPI vkapi) {
        super(message, vkapi);
    }

    @Override
    public IAction parse() throws IOException {
        return this;
    }

    @Override
    public IAction makeMessage() throws IOException {
        return this;
    }

    @Override
    public IAction sendMessage() throws VKAPIException {
        sendResult("", new int[0],
                "Испольование:\n" +
                        "!player [arg]\n" +
                        "Доступные аргументы:\n" +
                        "!help - Вызов этого меню.\n" +
                        "!time - Точное время\n" +
                        "!player - Управление плеером\n" +
                        "!zip - Запаковка фото, аудио документов в архив.",
                0, 0, 0, "", new int[0]);
        return this;
    }
}
