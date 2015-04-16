package com.redprojects.bot.actions;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import com.redprojects.utils.config.arguments.ProgramArguments;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Player extends IAction {

    interface Control {
        void setPlaylist(JSONArray playlist) throws IOException;

        void play() throws IOException;

        void pause() throws IOException;

        void stop() throws IOException;

        void next() throws IOException;

        void prev() throws IOException;

        void mute() throws IOException;

        void volup() throws IOException;

        void voldwn() throws IOException;
    }

    class AIMP3 implements Control {
        private final String PATH = ProgramArguments.getInstance().get("xbmcServer").getValue().toString();
        private File TEMP_FILE = new File("./tmp.m3u");

        @Override
        public void setPlaylist(JSONArray attachments) throws IOException {
            String playlist = "";
            for (int i = 0; i < attachments.length(); i++)
                if (attachments.getJSONObject(i).getString("type").equals("audio")) {
                    JSONObject attachement = attachments.getJSONObject(i).getJSONObject("audio");
                    playlist += "#EXTINF:" + attachement.getInt("duration") + "," +
                            attachement.getString("artist") + " - " + attachement.getString("title") + "\n" +
                            attachement.getString("url") + "\n";
                }
            if (!playlist.equals("#EXTM3U\n")) {
                Utils.copyStringToFile(playlist, TEMP_FILE);
                Runtime.getRuntime().exec(PATH + " " + "/ADD_PLAY" + " \"" + TEMP_FILE.getAbsolutePath() + "\"");
            }
        }

        @Override
        public void play() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/PLAY");
        }

        @Override
        public void pause() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/PAUSE");
        }

        @Override
        public void stop() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/STOP");
        }

        @Override
        public void next() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/NEXT");
        }

        @Override
        public void prev() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/PREV");
        }

        @Override
        public void mute() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/MUTE");
        }

        @Override
        public void volup() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/VOLUP");
        }

        @Override
        public void voldwn() throws IOException {
            Runtime.getRuntime().exec(PATH + " " + "/VOLDWN");
        }
    }

    class XBMC implements Control {
        private final String MAIN_URL = ProgramArguments.getInstance().get("xbmcServer").toString();

        private String executePost(String targetURL, String contentType, Object urlParameters) throws IOException {
            //Create connection
            URL url = new URL(targetURL);

            URLConnection connection = url.openConnection();

            if (url.getProtocol().equals("https"))
                ((HttpsURLConnection) connection).setRequestMethod("POST");
            else
                ((java.net.HttpURLConnection) connection).setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);

            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.toString().getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters.toString());
            wr.flush();
            wr.close();

            //Get Response
            return Utils.inputStreamToString(connection.getInputStream(), true);
        }

        private JSONObject sendData(String method, JSONObject params) {
            JSONObject data = new JSONObject();
            data.put("jsonrpc", "2.0");
            data.put("method", method);
            data.put("id", 1);
            data.put("params", params);
            try {
                return new JSONObject(executePost(MAIN_URL, "application/json", data));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        public void setPlaylist(JSONArray playlist) throws IOException {
            JSONObject params = new JSONObject().put("playlistid", 0).put("item", new JSONObject().put("file", ""));
            if (sendData("Playlist.Clear", new JSONObject().put("playlistid", 0)).getString("result").equals("OK")) {
                for (int i = 0; i < attachments.length(); i++)
                    if (attachments.getJSONObject(i).getString("type").equals("audio")) {
                        params.getJSONObject("item").put("file", attachments.getJSONObject(i).getJSONObject("audio").getString("url"));
                        sendData("Playlist.Add", params);
                    }
                sendData("Player.Open", new JSONObject().put("item", new JSONObject().put("playlistid", 0).put("position", 0)));
            }
        }

        @Override
        public void play() throws IOException {
            if (sendData("Player.PlayPause", new JSONObject().put("playerid", 0).put("play", true)).has("error"))
                sendData("Player.Open", new JSONObject().put("item", new JSONObject().put("playlistid", 0).put("position", 0)));
        }

        @Override
        public void pause() throws IOException {
            sendData("Player.PlayPause", new JSONObject().put("playerid", 0).put("play", false));
        }

        @Override
        public void stop() throws IOException {
            sendData("Player.Stop", new JSONObject().put("playerid", 0));
        }

        @Override
        public void next() throws IOException {
            sendData("Player.GoTo", new JSONObject().put("playerid", 0).put("to", "next"));
        }

        @Override
        public void prev() throws IOException {
            sendData("Player.GoTo", new JSONObject().put("playerid", 0).put("to", "previous"));
        }

        @Override
        public void mute() throws IOException {
            sendData("Application.SetMute", new JSONObject().put("mute", true));
        }

        @Override
        public void volup() throws IOException {
            int volume = sendData("Application.GetProperties", new JSONObject().put("properties", new JSONArray().put("volume"))).getJSONObject("result").getInt("volume");
            sendData("Application.SetVolume", new JSONObject().put("volume", volume + 2));
        }

        @Override
        public void voldwn() throws IOException {
            int volume = sendData("Application.GetProperties", new JSONObject().put("properties", new JSONArray().put("volume"))).getJSONObject("result").getInt("volume");
            sendData("Application.SetVolume", new JSONObject().put("volume", volume - 2));
        }
    }

    @SuppressWarnings("unchecked")
    private static final List<String> allowedUsers = (List<String>) ProgramArguments.getInstance().get("playerAllowedUsers").getValue();
    private boolean allowed;

    private boolean done = true;

    private JSONArray attachments;

    //TODO: Добавить аргументы
    private static Control control;

    private String action;

    public Player(JSONObject message, VKAPI vkapi) {
        super(message, vkapi);
        if (control == null) {
            if (!ProgramArguments.getInstance().get("player").isSet()) {
                control = new XBMC();
                return;
            }
            if (ProgramArguments.getInstance().get("player").getValue().equals("XBMC"))
                control = new XBMC();
            else if (ProgramArguments.getInstance().get("player").getValue().equals("XBMC"))
                control = new AIMP3();
        }
    }

    @Override
    public IAction parse() throws IOException {
        allowed = allowedUsers.contains(String.valueOf(message.getInt("user_id")));
        if (!allowed)
            return this;

        String[] body = message.getString("body").split(" ");
        action = body.length == 2 ? body[1] : "";

        //noinspection IfCanBeSwitch
        if (action.equals("play") || action.equals(">"))
            control.play();
        else if (action.equals("pause") || action.equals("||"))
            control.pause();
        else if (action.equals("stop") || action.equals("S"))
            control.stop();
        else if (action.equals("next") || action.equals("»"))
            control.next();
        else if (action.equals("prev") || action.equals("«"))
            control.prev();
        else if (action.equals("mute") || action.equals("M"))
            control.mute();
        else if (action.equals("volup") || action.equals("VU"))
            control.volup();
        else if (action.equals("voldwn") || action.equals("VD"))
            control.voldwn();
        else if (action.equals("new") || action.equals("play") || action.equals("N")) {
            done = false;
            if (message.has("attachments"))
                attachments = message.getJSONArray("attachments");
            else
                attachments = new JSONArray();

            if (attachments.length() == 1 && attachments.getJSONObject(0).getString("type").equals("wall")) {
                JSONObject wall = attachments.getJSONObject(0).getJSONObject("wall");
                if (wall.has("copy_history"))
                    wall = wall.getJSONArray("copy_history").getJSONObject(0);
                attachments = wall.getJSONArray("attachments");
            }
        }
        return this;
    }

    @Override
    public IAction makeMessage() throws IOException {
        if (!allowed || done)
            return this;
        control.setPlaylist(attachments);
        return this;
    }

    @Override
    public IAction sendMessage() throws VKAPIException {
        if (!allowed) {
            sendResult("", new int[0], "Доступ запрешён.", 0, 0, 0, "", new int[0]);
            Log.console(this, "Пользователя " + message.getInt("user_id") + " нет в списке. Пропускаю.");
            return this;
        }
        if (action.equals("help") || action.equals("?") || action.isEmpty())
            sendResult("", new int[0],
                    "Доступные аргументы:\n" +
                            "help (?) Показать это меню\n" +
                            "play (>) Начать проигрывание\n" +
                            "pause (||) Приостановить проигрывание\n" +
                            "stop\n" +
                            "next\n" +
                            "prev\n" +
                            "mute\n" +
                            "volup\n" +
                            "voldwn\n" +
                            "new\n",
                    0, 0, 0, "", new int[0]);
        return this;
    }
}
