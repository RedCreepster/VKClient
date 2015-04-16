package com.redprojects.bot.actions;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Messages;
import org.json.JSONObject;

import java.io.IOException;

public abstract class IAction {
    protected final JSONObject message;
    protected final VKAPI vkapi;
    protected Messages messages;
    protected final int chatId;

    public IAction(JSONObject message, VKAPI vkapi) {
        this.message = message;
        this.vkapi = vkapi;
        messages = new Messages(vkapi);
        if (message.has("chat_id"))
            chatId = message.getInt("chat_id");
        else
            chatId = 0;
    }

    public abstract IAction parse() throws IOException;

    public abstract IAction makeMessage() throws IOException;

    public abstract IAction sendMessage() throws VKAPIException;

    protected void sendResult(
            String domain, int[] user_ids, String message, int guid,
            float lat, float _long, String attachment, int[] forward_messages
    ) throws VKAPIException {
        messages.send(chatId > 0 ? 0 : this.message.getInt("user_id"), domain, chatId, user_ids, message, guid, lat, _long, attachment, forward_messages);
    }
}
