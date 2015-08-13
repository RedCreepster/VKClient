package com.redprojects.vk.api.objects;

import org.json.JSONObject;

public class PhotoExtended extends Photo {

    private boolean userLikes;
    private int likesCount;
    private int commentsCount;
    private boolean canComment;
    private boolean canRepost;
    private int tagsCount;

    public PhotoExtended(Photo photo) {
        this(photo.jsonObject);
    }

    public PhotoExtended(JSONObject photo) {
        super(photo);
        this.userLikes = photo.has("likes") && photo.getJSONObject("likes").getInt("user_likes") == 1;
        this.likesCount = photo.has("likes") ? photo.getJSONObject("likes").getInt("count") : 0;
        this.commentsCount = photo.has("comments") ? photo.getJSONObject("comments").getInt("count") : 0;
        this.canComment = photo.getInt("can_comment") == 1;
        this.canRepost = photo.has("can_repost") && photo.getInt("can_repost") == 1;
        this.tagsCount = photo.has("tags") ? photo.getJSONObject("tags").getInt("count") : 0;
    }

    public boolean isUserLikes() {
        return userLikes;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public boolean isCanRepost() {
        return canRepost;
    }

    public int getTagsCount() {
        return tagsCount;
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
                .put("date", date.getTime())
                .put("likes", new JSONObject().put("user_likes", userLikes ? 1 : 0).put("count", likesCount))
                .put("comments", new JSONObject().put("count", commentsCount))
                .put("can_comments", canComment)
                .put("can_repost", canRepost)
                .put("tags", new JSONObject().put("count", tagsCount)).toString();
    }
}
