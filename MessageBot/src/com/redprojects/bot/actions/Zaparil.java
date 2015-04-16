package com.redprojects.bot.actions;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import java.io.IOException;

public class Zaparil extends IAction {
    public Zaparil(JSONObject message, VKAPI vkapi) {
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
        sendResult("", new int[0], "И тебе привет, Роман.", 0, 0, 0, "", new int[0]);
        return this;
    }
}
