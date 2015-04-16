package com.redprojects.vk.api;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LongPoll extends ArrayList<LongPoll.Response> implements Runnable {

    public interface Response {
        void message(int messageId);
    }

    private final String server;
    private final String key;
    private int ts;
    private final int wait;

    public LongPoll(String server, String key, int ts, int wait) {
        this.server = server;
        this.key = key;
        this.ts = ts;
        this.wait = wait;
    }

    public LongPoll getNewInstanse() {
        return new LongPoll(server, key, ts, wait);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                String r = Utils.executeGet("https://" + server + "?act=a_check&key=" + key + "&ts=" + ts + "&wait=" + wait + "&mode=2");
                JSONObject jsonResponse = new JSONObject(r);
                ts = jsonResponse.getInt("ts");
                JSONArray updates = jsonResponse.getJSONArray("updates");
                if (jsonResponse.getJSONArray("updates").length() > 0)
                    for (int i = 0; i < updates.length(); i++) {
                        JSONArray items = updates.getJSONArray(i);
                        int action = items.getInt(0);
                        if (action == 4)
                            for (Response response : this)
                                response.message(items.getInt(1));
                    }
            } catch (IOException e) {
                Log.console(this, "Ошибка подключения. Пробуем снова.");
            }
        }
    }
}
