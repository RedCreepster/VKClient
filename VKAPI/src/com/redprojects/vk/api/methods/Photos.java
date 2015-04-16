package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

//TODO: Доделать
public class Photos extends Method {
    public Photos(VKAPI vkapi) {
        super(vkapi);
    }

    public static JSONObject get(
            int owner_id, int album_id, int[] photo_ids, boolean rev,
            boolean extended, String feed_type, int feed,
            boolean photo_sizes, int offset, int count
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufPhotoIds = "";
        for (int photo_id : photo_ids)
            bufPhotoIds += String.valueOf(photo_id) + ",";

        return VKAPI.getResponseWithoutAuthorization("photos." + methodName, new String[][]{
                {"owner_id", String.valueOf(owner_id)},
                {"album_id", String.valueOf(album_id)},
                {"photo_ids", bufPhotoIds},
                {"rev", rev ? "1" : "0"},
                {"extended", extended ? "1" : "0"},
                {"feed_type", feed_type},
                {"feed", String.valueOf(feed)},
                {"photo_sizes", photo_sizes ? "1" : "0"},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }
}
