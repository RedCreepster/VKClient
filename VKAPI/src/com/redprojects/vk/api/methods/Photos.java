package com.redprojects.vk.api.methods;

import com.redprojects.utils.Utils;
import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.objects.Photo;
import org.json.JSONArray;
import org.json.JSONObject;

//TODO: Доделать
public class Photos extends Method {

    public enum AlbumId {
        wall, profile, saved, preview;

        public static AlbumId fromInt(int album_id) {
            switch (album_id) {
                case -6:
                    return profile;
                case -7:
                    return wall;
                case -15:
                    return saved;
            }
            return preview;
        }
    }

    public Photos(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Создает пустой альбом для фотографий.
     *
     * @param title                 Название альбома.
     *                              Минимальная длина 2
     * @param group_id              Идентификатор сообщества, в котором создаётся альбом.
     * @param description           Текст описания альбома.
     * @param privacy_view          Настройки приватности просмотра альбома. PrivacyType
     * @param privacy_comment       Настройки приватности комментирования альбома. PrivacyType
     * @param upload_by_admins_only Кто может загружать фотографии в альбом (только для альбома сообщества):
     *                              true - фотографии могут добавлять только редакторы и администраторы,
     *                              false - фотографии могут добавлять все пользователи.
     * @param comments_disabled     Отключено ли комментирование альбома (только для альбома сообщества).
     * @return После успешного выполнения возвращает объект, который содержит следующие поля:
     * id — идентификатор созданного альбома;
     * thumb_id — идентификатор фотографии, которая является обложкой альбома (0, если обложка отсутствует);
     * owner_id идентификатор пользователя или сообщества, которому принадлежит альбом;
     * title— название альбома;
     * description — описание альбома;
     * created — дата создания альбома в формате unixtime;
     * updated — дата обновления альбома в формате unixtime;
     * size — количество фотографий в альбоме;
     * privacy — настройки приватности для просмотра альбома (только для альбома пользователя);
     * comment_privacy — настройки приватности для комментирования альбома (только для альбома пользователя);
     * upload_by_admins_only — кто может загружать фотографии в альбом (только для альбома сообщества);
     * comments_disabled — отключено ли комментирование альбома (только для альбома сообщества);
     * can_upload — может ли текущий пользователь добавлять фотографии в альбом.
     * @throws VKAPIException 302 Создано максимальное количество альбомов.
     */
    public JSONObject createAlbum(
            String title, int group_id, String description, String[] privacy_view,
            String[] privacy_comment, boolean upload_by_admins_only, boolean comments_disabled
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponse("photos." + methodName, new String[][]{
                {"title", title},
                {"group_id", String.valueOf(group_id)},
                {"description", description},
                {"privacy_view", Utils.concat(privacy_view)},
                {"privacy_comment", Utils.concat(privacy_comment)},
                {"upload_by_admins_only", upload_by_admins_only ? "1" : "0"},
                {"comments_disabled", comments_disabled ? "1" : "0"},
        });
    }


    /**
     * Редактирует данные альбома для фотографий пользователя.
     *
     * @param album_id              Идентификатор альбома.
     * @param title                 Название альбома.
     *                              Минимальная длина 2
     * @param description           Текст описания альбома.
     * @param owner_id              Идентификатор владельца альбома (пользователь или сообщество).
     *                              Обратите внимание, идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-".
     * @param privacy_view          Настройки приватности просмотра альбома. PrivacyType
     * @param privacy_comment       Настройки приватности комментирования альбома. PrivacyType
     * @param upload_by_admins_only Кто может загружать фотографии в альбом (только для альбома сообщества):
     *                              true - фотографии могут добавлять только редакторы и администраторы,
     *                              false - фотографии могут добавлять все пользователи.
     * @param comments_disabled     Отключено ли комментирование альбома (только для альбома сообщества).
     * @return После успешного выполнения возвращает 1.
     * @throws VKAPIException 114 Недопустимый идентификатор альбома.
     */
    public JSONObject editAlbum(
            int album_id, String title, String description, int owner_id, String[] privacy_view,
            String[] privacy_comment, boolean upload_by_admins_only, boolean comments_disabled
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponse("photos." + methodName, new String[][]{
                {"group_id", String.valueOf(album_id)},
                {"title", title},
                {"description", description},
                {"group_id", String.valueOf(owner_id)},
                {"privacy_view", Utils.concat(privacy_view)},
                {"privacy_comment", Utils.concat(privacy_comment)},
                {"upload_by_admins_only", upload_by_admins_only ? "1" : "0"},
                {"comments_disabled", comments_disabled ? "1" : "0"},
        });
    }

    public static Photo[] get(
            int owner_id, AlbumId album_id, int[] photo_ids, boolean rev,
            boolean extended, String feed_type, int feed,
            boolean photo_sizes, int offset, int count
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufPhotoIds = "";
        for (int photo_id : photo_ids)
            bufPhotoIds += String.valueOf(photo_id) + ",";

        JSONArray items = getResponse("photos." + methodName, new String[][]{
                {"owner_id", String.valueOf(owner_id)},
                {"album_id", album_id.name()},
                {"photo_ids", bufPhotoIds},
                {"rev", rev ? "1" : "0"},
                {"extended", extended ? "1" : "0"},
                {"feed_type", feed_type},
                {"feed", String.valueOf(feed)},
                {"photo_sizes", photo_sizes ? "1" : "0"},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        }).getJSONObject("response").getJSONArray("items");

        Photo[] photos = new Photo[items.length()];
        for (int i = 0; i < items.length(); i++)
            photos[i] = new Photo(items.getJSONObject(i));
        return photos;

    }

}
