package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import static com.redprojects.vk.api.VKAPI.getResponseWithoutAuthorization;

//TODO: Доделать
public class Wall extends Method {

    public enum Filter {
        suggests, postponed, owner, others, all
    }

    public Wall() {
        super(null);
    }

    public Wall(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Возвращает список записей со стены пользователя или сообщества.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param owner_id Идентификатор пользователя или сообщества, со стены которого необходимо получить записи (по умолчанию — текущий пользователь).
     *                 Обратите внимание, идентификатор сообщества в параметре owner_id необходимо указывать со знаком "-" — например, owner_id=-1 соответствует идентификатору сообщества ВКонтакте API (club1)
     * @param domain   Короткий адрес пользователя или сообщества.
     * @param offset   Смещение, необходимое для выборки определенного подмножества записей.
     *                 Положительное число
     * @param count    Количество записей, которое необходимо получить (но не более 100).
     *                 Положительное число
     * @param filter   Определяет, какие типы записей на стене необходимо получить.
     *                 Возможны следующие значения параметра:<br />
     *                 suggests — предложенные записи на стене сообщества;<br />
     *                 postponed — отложенные записи (доступно только при вызове с передачей access_token);<br />
     *                 owner — записи на стене от ее владельца;<br />
     *                 others — записи на стене не от ее владельца;<br />
     *                 all — все записи на стене (owner + others).
     *                 Указаны в enum Filter.
     * @param extended 1 — будут возвращены три массива wall, profiles и groups.
     *                 По умолчанию дополнительные поля не возвращаются.
     *                 Флаг, может принимать значения 1 или 0
     * @return После успешного выполнения возвращает список объектов записей на стене.
     * <p/>
     * Если был задан параметр extended=1, возвращает отдельно списки объектов записей на стене в поле items, пользователей с дополнительными полями photo и online в поле profiles, и сообществ в поле groups.
     * @throws VKAPIException 18 Страница удалена или заблокирована.
     * @throws VKAPIException 19 Контент недоступен.
     */
    public JSONObject get(int owner_id, String domain, int offset, int count, Filter filter, int extended) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"owner_id", String.valueOf(owner_id)},
                {"domain", domain},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"filter", filter.name()},
                {"extended", String.valueOf(extended)}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }
}
