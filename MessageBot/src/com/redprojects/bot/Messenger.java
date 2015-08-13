package com.redprojects.bot;

import com.redprojects.bot.actions.*;
import com.redprojects.utils.Log;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.vk.api.LongPoll;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.GlobalException;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Messages;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Messenger implements LongPoll.Response {

    private VKAPI vkapi;
    private Messages messages;

    public Messenger(VKAPI vkapi) {
        this.vkapi = vkapi;
        if (ProgramArguments.getInstance().get("getLoginUrl").getValue().equals(true) || ProgramArguments.getInstance().get("token").getValue() == null) {
            Log.console(this, vkapi.getLoginUrl());
            System.exit(0);
        }
        messages = new Messages(vkapi);
    }

    public LongPoll startLongPoll() throws VKAPIException {
        JSONObject response = messages.getLongPollServer(true, false).getJSONObject("response");
        LongPoll longPoll = new LongPoll(response.getString("server"), response.getString("key"), response.getInt("ts"), 25);
        longPoll.add(this);
        return longPoll;
    }

    @Override
    public void message(int messageId) {
        try {
            try {
                JSONObject msg = messages.getById(new int[]{messageId}, 0);
                JSONArray items = msg.getJSONObject("response").getJSONArray("items");

                if (items.length() > 0)
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject message = items.getJSONObject(i);
                        if (message.getString("body").startsWith("!")) {
                            String body = message.getString("body");
                            Log.console(this, "Новое сообщение от id: " + message.getInt("user_id") + " с текстом " + body + ".");
                            if (body.equals("!help"))
                                new Help(message, vkapi).parse().makeMessage().sendMessage();
                            else if (body.equals("!zip"))
                                new ZIP(message, vkapi).parse().makeMessage().sendMessage();
                            else if (body.equals("!time"))
                                new Time(message, vkapi).parse().makeMessage().sendMessage();
                            else if (body.startsWith("!player") || body.startsWith("!pl"))
                                new Player(message, vkapi).parse().makeMessage().sendMessage();
                            else if (body.equals("!zaparil"))
                                new Zaparil(message, vkapi).parse().makeMessage().sendMessage();
                        }
                    }
            } catch (VKAPIException e) {
                if (e instanceof GlobalException && (e.getCode() == 6 || e.getCode() == 9 || e.getCode() == 10))
                    Log.console(this, e.getMessage() + "\nПропуск действия.");
                else
                    e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
