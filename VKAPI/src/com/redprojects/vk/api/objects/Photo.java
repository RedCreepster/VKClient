package com.redprojects.vk.api.objects;

import com.redprojects.vk.api.methods.Photos;
import org.json.JSONObject;

import java.util.Date;

//TODO: Доделать сохранение фото
public class Photo extends Object {

    protected JSONObject jsonObject;

    protected int id;
    protected Photos.AlbumId albumId;
    protected int ownerId;
    protected int userId;
    protected String photo_75;
    protected String photo_130;
    protected String photo_604;
    protected String photo_807;
    protected String photo_1280;
    protected String photo_2560;
    protected int width;
    protected int height;
    protected String text;
    protected Date date;

    public Photo(JSONObject photo) {
        this(photo.getInt("id"),
                Photos.AlbumId.fromInt(photo.getInt("album_id")),
                photo.getInt("owner_id"),
                photo.has("user_id") ? photo.getInt("user_id") : 0,
                photo.getString("photo_75"),
                photo.has("photo_130") ? photo.getString("photo_130") : null,
                photo.has("photo_604") ? photo.getString("photo_604") : null,
                photo.has("photo_807") ? photo.getString("photo_807") : null,
                photo.has("photo_1280") ? photo.getString("photo_1280") : null,
                photo.has("photo_2560") ? photo.getString("photo_2560") : null,
                photo.has("width") ? photo.getInt("width") : 0,
                photo.has("height") ? photo.getInt("height") : 0,
                photo.getString("text"),
                new Date(photo.getLong("date")));
        jsonObject = photo;
    }

    public Photo(int id, Photos.AlbumId albumId, int ownerId, int userId, String photo_75,
                 String photo_130, String photo_604, String photo_807, String photo_1280,
                 String photo_2560, int width, int height, String text, Date date) {
        this.id = id;
        this.albumId = albumId;
        this.ownerId = ownerId;
        this.userId = userId;
        this.photo_75 = photo_75;
        this.photo_130 = photo_130;
        this.photo_604 = photo_604;
        this.photo_807 = photo_807;
        this.photo_1280 = photo_1280;
        this.photo_2560 = photo_2560;
        this.width = width;
        this.height = height;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Photos.AlbumId getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Photos.AlbumId albumId) {
        this.albumId = albumId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhoto_75() {
        return photo_75;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public String getPhoto_807() {
        return photo_807;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public String getPhoto_2560() {
        return photo_2560;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return new JSONObject().put("id", id)
                .put("album_id", albumId.name())
                .put("owner_id", id)
                .put("user_id", id)
                .put("photo_75", photo_75)
                .put("photo_130", photo_130)
                .put("photo_604", photo_604)
                .put("photo_807", photo_807)
                .put("photo_1280", photo_1280)
                .put("photo_2560", photo_2560)
                .put("width", width)
                .put("height", height)
                .put("text", text)
                .put("date", date.getTime()).toString();
    }
}
