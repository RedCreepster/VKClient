package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

/**
 * Методы audio позволяют работать с аудиозаписями пользователей и сообществ.
 * Для вызова этих методов Ваше приложение должно иметь следующие права: audio.
 * Обратите внимание, что ссылки на аудиозаписи привязаны к ip адресу.
 */
@SuppressWarnings("UnusedDeclaration")
public class Audio extends Method {

    public Audio(VKAPI vkapi) {
        super(vkapi);
    }

    @SuppressWarnings("UnusedDeclaration")
    public enum Filter {
        friends, groups, all
    }

    /**
     * Возвращает список аудиозаписей пользователя или сообщества.
     *
     * @param owner_id  Идентификатор владельца аудиозаписей (пользователь или сообщество).
     *                  Обратите внимание, идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например, owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1)<br />
     *                  По умолчанию идентификатор текущего пользователя
     * @param album_id  Идентификатор альбома с аудиозаписями.
     * @param audio_ids Идентификаторы аудиозаписей, информацию о которых необходимо вернуть.
     *                  Список положительных чисел, разделенных запятыми
     * @param need_user 1 — возвращать информацию о пользователях, загрузивших аудиозапись.
     *                  Флаг, может принимать значения 1 или 0
     * @param offset    Смещение, необходимое для выборки определенного количества аудиозаписей. По умолчанию — 0.
     *                  Положительное число
     * @param count     Количество аудиозаписей, информацию о которых необходимо вернуть. Максимальное значение — 6000.
     *                  Положительное число
     *                  Обратите внимание, что даже с использованием параметра offset получить информацию об аудиозаписях, находящихся после первых 6 тысяч в списке пользователя или сообщества, невозможно.
     * @return После успешного выполнения возвращает список объектов audio.
     * <p/>
     * Если был задан параметр need_user=1, дополнительно возвращается объект user, содержащий поля:<br />
     * id — идентификатор пользователя;<br />
     * photo — url фотографии профиля;<br />
     * name — имя и фамилия пользователя;<br />
     * name_gen — имя пользователя в родительном падеже.
     * <p/>
     * <table>
     * <tr><td><span class="throwsLabel">Коды ошибок:</span></td></tr>
     * <tr>
     * <td>19</td>
     * <td>Контент недоступен.</td>
     * </tr>
     * </table>
     */
    public JSONObject get(int owner_id, int album_id, int[] audio_ids, int need_user, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufAudio_ids = "";
        for (int audio_id : audio_ids)
            bufAudio_ids += String.valueOf(audio_id) + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(owner_id)},
                {"album_id", String.valueOf(album_id)},
                {"audio_ids", bufAudio_ids},
                {"need_user", String.valueOf(need_user)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Возвращает информацию об аудиозаписях.
     *
     * @param audios Идентификаторы аудиозаписей, информацию о которых необходимо вернуть, в виде {owner_id}_{audio_id}.
     *               ''''обязательный параметр''''
     * @return После успешного выполнения возвращает список объектов audio.
     */
    public JSONObject getById(String[] audios) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufAudios = "";
        for (String audio : audios) {
            bufAudios += String.valueOf(audio) + ",";
        }
        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audios", bufAudios}
        });
    }

    /**
     * Возвращает текст аудиозаписи.
     *
     * @param lyrics_id Идентификатор текста аудиозаписи, информацию о котором необходимо вернуть.
     *                  Может быть получен с помощью методов audio.message, audio.getById или audio.search.
     *                  ''''обязательный параметр''''
     * @return После успешного выполнения возвращает объект lyrics c полями lyrics_id — идентификатор текста и text — текст аудиозаписи..
     * В качестве переводов строк в тексте используется /n.
     */
    public JSONObject getLyrics(int lyrics_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"lyrics_id", String.valueOf(lyrics_id)}
        });
    }

    /**
     * Возвращает список аудиозаписей в соответствии с заданным критерием поиска.
     *
     * @param q              Текст поискового запроса, например, The Beatles.
     * @param auto_complete  Если этот параметр равен 1, возможные ошибки в поисковом запросе будут исправлены. Например, при поисковом запросе Иуфдуы поиск будет осуществляться по строке Beatles.
     *                       Флаг, может принимать значения 1 или 0
     * @param lyrics         Если этот параметр равен 1, поиск будет производиться только по тем аудиозаписям, которые содержат тексты.
     *                       Флаг, может принимать значения 1 или 0
     * @param performer_only Если этот параметр равен 1, поиск будет осуществляться только по названию исполнителя.
     *                       Флаг, может принимать значения 1 или 0
     * @param sort           Вид сортировки. 2 — по популярности, 1 — по длительности аудиозаписи, 0 — по дате добавления.
     * @param search_own     1 – искать по аудиозаписям пользователя, 0 – не искать по аудиозаписям пользователя (по умолчанию).
     *                       Флаг, может принимать значения 1 или 0
     * @param offset         Смещение, необходимое для выборки определенного подмножетсва аудиозаписей. По умолчанию — 0.
     *                       Положительное число
     * @param count          Количество аудиозаписей, информацию о которых необходимо вернуть.
     *                       Обратите внимание — даже при использовании параметра offset для получения информации доступны только первые 1000 результатов.
     *                       Положительное число, по умолчанию 30, максимальное значение 300
     * @return После успешного выполнения возвращает список объектов audio.
     */
    public JSONObject search(
            String q, int auto_complete, int lyrics, int performer_only,
            int sort, int search_own, int offset, int count
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"q", q},
                {"auto_complete", String.valueOf(auto_complete)},
                {"lyrics", String.valueOf(lyrics)},
                {"count", String.valueOf(count)},
                {"performer_only", String.valueOf(performer_only)},
                {"sort", String.valueOf(sort)},
                {"search_own", String.valueOf(search_own)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Возвращает адрес сервера для загрузки аудиозаписей.
     *
     * @return После успешного выполнения возвращает объект с единственным полем upload_url.
     */
    public JSONObject getUploadServer() throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{{}});
    }

    /**
     * Сохраняет аудиозаписи после успешной загрузки.
     *
     * @param server Параметр, возвращаемый в результате загрузки аудиофайла на сервер.
     *               ''''обязательный параметр''''
     * @param audio  Параметр, возвращаемый в результате загрузки аудиофайла на сервер.
     *               ''''обязательный параметр''''
     * @param hash   Параметр, возвращаемый в результате загрузки аудиофайла на сервер.
     * @param artist Автор композиции. По умолчанию берется из ID3 тегов.
     * @param title  Название композиции. По умолчанию берется из ID3 тегов.
     * @return Возвращает массив из объектов с загруженными аудиозаписями, каждый из которых имеет поля id, owner_id, artist, title, url.
     * <table>
     * <tr><td><span class="throwsLabel">Коды ошибок:</span></td></tr>
     * <tr>
     * <td>121</td>
     * <td>Неверный хэш.</td>
     * </tr>
     * <tr>
     * <td>123</td>
     * <td>Недопустимый формат аудиозаписи.</td>
     * </tr>
     * <tr>
     * <td>270</td>
     * <td>Аудиозапись была изъята по запросу правообладателя и не может быть загружена.</td>
     * </tr>
     * <tr>
     * <td>301</td>
     * <td>Недопустимое имя файла.</td>
     * </tr>
     * <tr>
     * <td>302</td>
     * <td>Недопустимый размер файла.</td>
     * </tr>
     * </table>
     */
    public JSONObject save(int server, String audio, String hash, String artist, String title) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"server", String.valueOf(server)},
                {"audio", audio},
                {"hash", hash},
                {"artist", artist},
                {"title", title}
        });
    }

    /**
     * Копирует аудиозапись на страницу пользователя или группы.
     *
     * @param audio_id Идентификатор аудиозаписи.
     *                 Положительное число, ''''обязательный параметр''''
     * @param owner_id Идентификатор владельца аудиозаписи (пользователь или сообщество).
     *                 ''''обязательный параметр''''
     * @param group_id Идентификатор сообщества (если аудиозапись необходимо скопировать в список сообщества).
     * @return После успешного выполнения возвращает идентификатор созданной аудиозаписи (aid).
     */
    public JSONObject add(int audio_id, int owner_id, int group_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio_id", String.valueOf(audio_id)},
                {"owner_id", String.valueOf(owner_id)},
                {"group_id", String.valueOf(group_id)}
        });
    }

    /**
     * Удаляет аудиозапись со страницы пользователя или сообщества.
     *
     * @param audio_id Идентификатор аудиозаписи.
     *                 Положительное число, ''''обязательный параметр''''
     * @param owner_id Идентификатор владельца аудиозаписи (пользователь или сообщество).
     *                 ''''обязательный параметр''''
     * @return После успешного выполнения возвращает 1.
     */
    public JSONObject delete(int audio_id, int owner_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio_id", String.valueOf(audio_id)},
                {"owner_id", String.valueOf(owner_id)}
        });
    }

    /**
     * Редактирует данные аудиозаписи на странице пользователя или сообщества.
     *
     * @param owner_id  Идентификатор владельца аудиозаписи (пользователь или сообщество).
     *                  ID сообщества должен быть отрицательным.
     *                  owner_id=1 — пользователь;
     *                  owner_id=-1 — сообщество.
     *                  ''''обязательный параметр''''
     * @param audio_id  Идентификатор аудиозаписи.
     *                  Положительное число, ''''обязательный параметр''''
     * @param artist    Новое название исполнителя.
     * @param title     Новое название композиции.
     * @param text      Новый текст аудиозаписи.
     * @param genre_id  Идентификатор жанра из списка аудио жанров.
     *                  Положительное число
     * @param no_search 1 — аудиозапись не будет доступна в поиске. По умолчанию — 0.
     *                  Флаг, может принимать значения 1 или 0
     * @return После успешного выполнения возвращает id текста, введенного пользователем (lyrics_id), если текст не был введен, вернет 0.
     */
    public JSONObject edit(int owner_id, int audio_id, String artist, String title, String text, int genre_id, int no_search) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio_id", String.valueOf(owner_id)},
                {"owner_id", String.valueOf(audio_id)},
                {"artist", artist},
                {"title", title},
                {"text", text},
                {"genre_id", String.valueOf(genre_id)},
                {"no_search", String.valueOf(no_search)}
        });
    }

    /**
     * Изменяет порядок аудиозаписи, перенося ее между аудиозаписями, идентификаторы которых переданы параметрами after и before.
     *
     * @param audio_id Идентификатор аудиозаписи, которую нужно переместить.
     *                 Положительное число, ''''обязательный параметр''''
     * @param owner_id Идентификатор владельца аудиозаписи (пользователь или сообщество). По умолчанию — идентификатор текущего пользователя.
     *                 По умолчанию идентификатор текущего пользователя
     * @param before   Идентификатор аудиозаписи, перед которой нужно поместить композицию aid.
     * @param after    Идентификатор аудиозаписи, после которой нужно поместить композицию aid.
     * @return После успешного выполнения возвращает 1.
     */
    public JSONObject reorder(int audio_id, int owner_id, int before, int after) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio_id", String.valueOf(audio_id)},
                {"owner_id", String.valueOf(owner_id)},
                {"before", String.valueOf(before)},
                {"after", String.valueOf(after)}
        });
    }

    /**
     * Восстанавливает аудиозапись после удаления.
     *
     * @param audio_id Идентификатор аудиозаписи.
     *                 Положительное число, ''''обязательный параметр''''
     * @param owner_id Идентификатор владельца аудиозаписи (пользователь или сообщество). По умолчанию — идентификатор текущего пользователя.
     *                 По умолчанию идентификатор текущего пользователя
     * @return В случае успешного восстановления аудиозаписи возвращает структуру audio, которая имеет поля aid, owner_id, artist, title, url.
     * Если время хранения удаленной аудиозаписи истекло (обычно это 20 минут), сервер вернет ошибку 202 (Cache expired).
     */
    public JSONObject restore(int audio_id, int owner_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio_id", String.valueOf(audio_id)},
                {"owner_id", String.valueOf(owner_id)}
        });
    }

    /**
     * Возвращает список альбомов аудиозаписей пользователя или группы.
     *
     * @param owner_id Идентификатор пользователя или сообщества, у которого необходимо получить список альбомов с аудио.
     *                 По умолчанию идентификатор текущего пользователя
     * @param offset   Смещение, необходимое для выборки определенного подмножества альбомов.
     *                 Положительное число
     * @param count    Количество альбомов, которое необходимо вернуть.
     *                 Положительное число, по умолчанию 50, максимальное значение 100
     * @return После успешного выполнения возвращает общее количество альбомов с аудиозаписями и массив объектов album, каждый из которых содержит следующие поля:<br />
     * id — идентификатор альбома;<br />
     * owner_id — идентификатор владельца альбома;<br />
     * title — название альбома.
     */
    public JSONObject getAlbums(int owner_id, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(owner_id)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Создает пустой альбом аудиозаписей.
     *
     * @param group_id Идентификатор сообщества (если альбом нужно создать в сообществе).
     *                 Положительное число
     * @param title    Название альбома.
     *                 ''''обязательный параметр''''
     * @return После успешного выполнения возвращает идентификатор (album_id) созданного альбома.
     * <table>
     * <tr><td><span class="throwsLabel">Коды ошибок:</span></td></tr>
     * <tr>
     * <td>302</td>
     * <td>Создано максимальное количество альбомов.</td>
     * </tr>
     * </table>
     */
    public JSONObject addAlbum(int group_id, String title) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(group_id)},
                {"title", title}
        });
    }

    /**
     * Редактирует название альбома аудиозаписей.
     *
     * @param group_id Идентификатор сообщества, которому принадлежит альбом.
     *                 Положительное число
     * @param album_id Идентификатор альбома.
     *                 Положительное число, ''''обязательный параметр''''
     * @param title    Новое название для альбома.
     *                 ''''обязательный параметр''''
     * @return После успешного выполнения возвращает 1.
     */
    public JSONObject editAlbum(int group_id, int album_id, String title) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(group_id)},
                {"album_id", String.valueOf(album_id)},
                {"title", title}
        });
    }

    /**
     * Удаляет альбом аудиозаписей.
     *
     * @param group_id Идентификатор сообщества, которому принадлежит альбом.
     *                 Положительное число
     * @param album_id Идентификатор альбома.
     *                 Положительное число, ''''обязательный параметр''''
     * @return После успешного выполнения возвращает 1.
     */
    public JSONObject deleteAlbum(int group_id, int album_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(group_id)},
                {"album_id", String.valueOf(album_id)}
        });
    }

    /**
     * Перемещает аудиозаписи в альбом.
     *
     * @param group_id  Идентификатор сообщества, в котором размещены аудиозаписи. Если параметр не указан, работа ведется с аудиозаписями текущего пользователя.
     *                  Положительное число
     * @param album_id  Идентификатор альбома, в который нужно переместить аудиозаписи.
     *                  Положительное число
     * @param audio_ids Идентификаторы аудиозаписей, которые требуется переместить.
     *                  Список положительных чисел, разделенных запятыми, ''''обязательный параметр''''
     * @return После успешного выполнения возвращает 1.
     * Обратите внимание, в одном альбоме не может быть более 1000 аудиозаписей.
     */
    public JSONObject moveToAlbum(int group_id, int album_id, int[] audio_ids) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufAudio_ids = "";
        for (int audio_id : audio_ids) {
            bufAudio_ids += String.valueOf(audio_id) + ",";
        }
        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(group_id)},
                {"album_id", String.valueOf(album_id)},
                {"audio_ids", bufAudio_ids}
        });
    }

    /**
     * Транслирует аудиозапись в статус пользователю или сообществу.
     * Для вызова этого метода Ваше приложение должно иметь следующие права: status.
     *
     * @param audio      Идентификатор аудиозаписи, которая будет отображаться в статусе, в формате owner_id_audio_id. Например, 1_190442705. Если параметр не указан, аудиостатус указанных сообществ и пользователя будет удален.
     * @param target_ids Перечисленные через запятую идентификаторы сообществ и пользователя, которым будет транслироваться аудиозапись. Идентификаторы сообществ должны быть заданы в формате "-gid", где gid - идентификатор сообщества. Например, 1,-34384434. По умолчанию аудиозапись транслируется текущему пользователю.
     *                   Список чисел, разделенных запятыми, например "1,2,3", количество элементов должно составлять не более 20
     * @return В случае успешного выполнения возвращает массив идентификаторов сообществ и пользователя, которым был установлен или удален аудиостатус.
     */
    public JSONObject setBroadcast(String audio, int[] target_ids) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufTarget_ids = "";
        for (int target_id : target_ids) {
            bufTarget_ids += String.valueOf(target_id) + ",";
        }
        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"audio", audio},
                {"target_ids", bufTarget_ids}
        });
    }

    /**
     * Возвращает список друзей и сообществ пользователя, которые транслируют музыку в статус.
     *
     * @param filter Определяет, какие типы объектов необходимо получить. Возможные значение содержатся в enum Filter.
     * @param active 1 — будут возвращены только друзья и сообщества, которые транслируют музыку в данный момент. По умолчанию возвращаются все.
     *               Флаг, может принимать значения 1 или 0
     * @return После успешного выполнения возвращает список объектов друзей и сообществ с дополнительным полем status_audio — объект аудиозаписи, установленной в статус (если аудиозапись транслируется в текущей момент).
     */
    public JSONObject getBroadcastList(Filter filter, int active) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"filter", filter.name()},
                {"active", String.valueOf(active)}
        });
    }

    /**
     * Возвращает список рекомендуемых аудиозаписей на основе списка воспроизведения заданного пользователя или на основе одной выбранной аудиозаписи.
     *
     * @param target_audio Идентификатор аудиозаписи, на основе которой будет строиться список рекомендаций.
     *                     Используется вместо параметра uid. Идентификатор представляет из себя разделённые знаком подчеркивания id пользователя,которому принадлежит аудиозапись, и id самой аудиозаписи.
     *                     Если аудиозапись принадлежит сообществу, то в качестве первого параметра используется -id сообщества.
     * @param user_id      Идентификатор пользователя для получения списка рекомендаций на основе его набора аудиозаписей (по умолчанию — идентификатор текущего пользователя).
     *                     Положительное число
     * @param offset       Смещение относительно первой найденной аудиозаписи для выборки определенного подмножества.
     *                     Положительное число
     * @param count        Количество возвращаемых аудиозаписей.
     *                     Положительное число, максимальное значение 1000, по умолчанию 100
     * @param shuffle      1 — включен случайный порядок.
     *                     Флаг, может принимать значения 1 или 0
     * @return После успешного выполнения возвращает список объектов audio.
     * <table>
     * <tr><td><span class="throwsLabel">Коды ошибок:</span></td></tr>
     * <tr>
     * <td>19</td>
     * <td>Контент недоступен.</td>
     * </tr>
     * </table>
     */
    public JSONObject getRecommendations(String target_audio, int user_id, int offset, int count, int shuffle) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"target_audio", target_audio},
                {"user_id", String.valueOf(user_id)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"shuffle", String.valueOf(shuffle)}
        });
    }

    /**
     * Возвращает список аудиозаписей из раздела "Популярное".
     *
     * @param only_eng 1 – возвращать только зарубежные аудиозаписи. 0 – возвращать все аудиозаписи. (по умолчанию)
     *                 Флаг, может принимать значения 1 или 0
     * @param genre_id Идентификатор жанра из списка жанров.
     *                 Положительное число
     * @param offset   Смещение, необходимое для выборки определенного подмножества аудиозаписей.
     *                 Положительное число
     * @param count    Количество возвращаемых аудиозаписей.
     *                 Положительное число, максимальное значение 1000, по умолчанию 100
     * @return После успешного выполнения возвращает список объектов audio.
     */
    public JSONObject getPopular(int only_eng, int genre_id, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"only_eng", String.valueOf(only_eng)},
                {"genre_id", String.valueOf(genre_id)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Возвращает количество аудиозаписей пользователя или сообщества.
     *
     * @param owner_id Идентификатор владельца аудиозаписей (пользователь или сообщество).
     *                 Обратите внимание, идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например, owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1)
     *                 ''''обязательный параметр''''
     * @return После успешного выполнения возвращает число, равное количеству аудиозаписей на странице пользователя или сообщества.
     */
    public JSONObject getCount(int owner_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"owner_id", String.valueOf(owner_id)}
        });
    }
}
