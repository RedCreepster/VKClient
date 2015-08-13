package com.redprojects.vk.api.objects;

import com.redprojects.utils.Utils;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import java.io.*;

public class Audio extends Object {
    private int id;
    private int ownerId;
    private String artist;
    private String title;
    private int duration;
    private String url;
    private int lyricsId;
    private int albumId;
    private int genreId;

    public static Audio upload(com.redprojects.vk.api.methods.Audio audioMethods, File file, Audio audioObject) {
        try {
            String url = audioMethods.getUploadServer();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Utils.copyStream(bis, byteArrayOutputStream);
            bis.close();
            JSONObject jsonObject = new JSONObject(Utils.executePost(url, new String[0][], "file", file.getName(), byteArrayOutputStream.toByteArray(), "audio/mpeg"));
            audioObject = new Audio(audioMethods.save(jsonObject.getInt("server"), jsonObject.getString("audio"), jsonObject.getString("hash"), audioObject.artist, audioObject.title).getJSONObject("response"));
        } catch (VKAPIException | IOException e) {
            e.printStackTrace();
        }
        return audioObject;
    }

    public Audio(JSONObject audio) {
        this(audio.getInt("id"),
                audio.getInt("owner_id"),
                audio.getString("artist"),
                audio.getString("title"),
                audio.getInt("duration"),
                audio.getString("url"),
                audio.has("lyrics_id") ? audio.getInt("lyrics_id") : -1,
                audio.has("album_id") ? audio.getInt("album_id") : -1,
                audio.has("genre_id") ? audio.getInt("genre_id") : -1);
    }

    public Audio(int id, int ownerId, String artist, String title, int duration, String url, int lyricsId, int albumId, int genreId) {
        this.id = id;
        this.ownerId = ownerId;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.url = url;
        this.lyricsId = lyricsId;
        this.albumId = albumId;
        this.genreId = genreId;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public int getLyricsId() {
        return lyricsId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void edit(com.redprojects.vk.api.methods.Audio audioMethods, String text, boolean no_search) throws VKAPIException {
        audioMethods.edit(ownerId, id, artist, title, text, genreId, no_search);
    }

    public String getFileName() {
        return this.getFullName()
                .replace("\\", "_")
                .replace("/", "_")
                .replace(":", "_")
                .replace("*", "_")
                .replace("?", "_")
                .replace("\"", "''")
                .replace("<", "_")
                .replace(">", "_")
                .replace("|", "_");
    }

    public String getFullName() {
        String artist = this.getArtist();
        if (artist.length() > 70)
            artist = artist.substring(0, 70);
        String title = this.getTitle();
        if (title.length() > 70)
            title = title.substring(0, 70);
        return artist.trim() + " - " + title.trim();
    }

    @Override
    public String toString() {
        return new JSONObject().put("id", id)
                .put("owner_id", ownerId)
                .put("artist", artist)
                .put("title", title)
                .put("duration", duration)
                .put("url", url)
                .put("lyrics_id", lyricsId)
                .put("album_id", albumId)
                .put("genre_id", genreId).toString();
    }
}
