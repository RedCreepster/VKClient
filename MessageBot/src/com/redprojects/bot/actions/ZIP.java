package com.redprojects.bot.actions;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Docs;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.redprojects.utils.Utils.copyStream;

public class ZIP extends IAction {

    private ByteArrayOutputStream byteArrayOutputStream;

    private JSONArray attachments;

    public ZIP(JSONObject message, VKAPI vkapi) throws IOException, VKAPIException {
        super(message, vkapi);
    }

    @Override
    public IAction parse() throws IOException {
        if (message.has("attachments")) {
            attachments = message.getJSONArray("attachments");
            Log.console(this, "Новый архив для id: " + message.getInt("user_id") + ".");
        } else
            attachments = new JSONArray();

        return this;
    }

    @Override
    public IAction makeMessage() throws IOException {
        if (attachments.length() == 0)
            return this;
        byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(byteArrayOutputStream);
        //out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(Constants.getInstance().get("root") + Constants.getInstance().get("temp_path") + "/" + String.valueOf(message.getInt("id")) + ".zip")));
        for (int i = 0; i < attachments.length(); i++) {
            JSONObject attachment = attachments.getJSONObject(i);
            String type = attachment.getString("type");
            URL url;
            switch (type) {
                case "audio":
                    url = getMusic(attachment.getJSONObject(type));
                    break;
                case "photo":
                    url = getPhoto(attachment.getJSONObject(type));
                    break;
                case "doc":
                    url = getDoc(attachment.getJSONObject(type));
                    break;
                default:
                    continue;
            }

            BufferedInputStream origin = null;
            try {
                origin = new BufferedInputStream(url.openStream());

                out.putNextEntry(new ZipEntry(url.getFile().substring(url.getFile().lastIndexOf("/") + 1)));

                copyStream(origin, out);
            } finally {
                if (origin != null)
                    origin.close();
            }
        }
        out.close();
        return this;
    }

    @Override
    public IAction sendMessage() throws VKAPIException {
        if (attachments.length() == 0) {
            sendResult("", new int[0], "Для создания архива необходимо прикрепить к этому сообщению аудио, фото или документ.", 0, 0, 0, "", new int[0]);
            return this;
        }
        Docs docs = new Docs(vkapi);
        String url = docs.getUploadServer(0).getJSONObject("response").getString("upload_url");
        String fileName = String.valueOf(message.getInt("id")) + ".zip";
        try {
            JSONObject response = new JSONObject(Utils.executePost(url, new String[0][], "file", fileName + ".zip", byteArrayOutputStream.toByteArray(), "application/zip"));
            if (response.has("file")) {
                response = docs.save(response.getString("file"), fileName, "RedMessageBot");

                JSONObject document = response.getJSONArray("response").getJSONObject(0);
                sendResult("", new int[0], "", 0, 0, 0, "doc" + document.getInt("owner_id") + "_" + document.getInt("id"), new int[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private URL getMusic(JSONObject audio) throws IOException {
        return new URL(audio.getString("url") + "/" + audio.getString("artist") + " - " + audio.getString("title") + ".mp3");
    }

    private URL getPhoto(JSONObject photo) throws IOException {
        String url = "";
        if (photo.has("photo_2560"))
            url = photo.getString("photo_2560");
        else if (photo.has("photo_1280"))
            url = photo.getString("photo_1280");
        else if (photo.has("photo_807"))
            url = photo.getString("photo_807");
        else if (photo.has("photo_604"))
            url = photo.getString("photo_604");
        else if (photo.has("photo_130"))
            url = photo.getString("photo_130");
        else if (photo.has("photo_75"))
            url = photo.getString("photo_75");

        return new URL(url);
    }

    private URL getDoc(JSONObject doc) throws MalformedURLException {
        return new URL(doc.getString("url") + "/" + doc.getString("title"));
    }
}
