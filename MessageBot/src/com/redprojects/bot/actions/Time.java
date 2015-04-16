package com.redprojects.bot.actions;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time extends IAction {
    public Time(JSONObject message, VKAPI vkapi) {
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
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.y HH:mm:ss");
        sendResult("", new int[0], "Точное время:\n" + dateFormat.format(date), 0, 0, 0, "", new int[0]);
        return this;
    }
}
