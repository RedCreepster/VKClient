package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.GlobalException;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

//TODO: Доделать
@SuppressWarnings("UnusedDeclaration")
public class Storage extends Method {

    public Storage(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Возвращает значение переменной, название которой передано в параметре key.
     * Задать значение позволяет метод storage.set.
     * Переменные могут храниться в двух областях видимости:
     * Пользовательская, переменная привязана к пользователю, другие пользователи не могут её прочитать.
     * Глобальная, переменная привязана к приложению, и работа с ней не зависит от пользователя.
     * Если метод вызывается не от имени приложения, то нужно передать параметр global для доступа к переменной.
     *
     * @param key     Название переменной.
     * @param keys    Список названий переменных, разделённых запятыми. Если указан этот параметр, то параметр key не учитывается.
     * @param user_id Id пользователя, переменная которого устанавливается, в случае если данные запрашиваются серверным методом.
     * @param global  Указывается true, если необходимо работать с глобальными переменными, а не с переменными пользователя.
     * @return Возвращает значение одной или нескольких переменных.
     * Если переменная на сервере отсутствует, то будет возвращена пустая строка.
     * @throws VKAPIException
     */
    public JSONObject get(String key, String[] keys, int user_id, boolean global) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufKeys = "";
        for (String aKey : keys)
            bufKeys += aKey + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"key", key},
                {"keys", bufKeys},
                {"user_id", String.valueOf(user_id)},
                {"global", global ? "1" : "0"}
        });
    }

    /**
     * Сохраняет значение переменной, название которой передано в параметре key.
     * Получить сохранённое значение позволяет метод storage.get.
     * Переменные могут храниться в двух областях видимости:
     * Пользовательская переменная привязана к пользователю, и только он или сервер приложения может получить к ней доступ.
     * Может быть создано не более 1000 переменных для каждого пользователя. Не более 1000 вызовов в час на каждого пользователя.
     * Глобальная переменная привязана к приложению, и работа с ней не зависит от пользователя.
     * Для того чтобы задать глобальную переменную при работе с API от имени пользователя, нужно передать параметр global.
     * Может быть создано не более 5000 глобальных переменных. Не более 10000 вызовов в час.
     *
     * @param key     Название переменной. Может содержать символы латинского алфавита, цифры, знак тире, нижнее подчёркивание [a-zA-Z_\-0-9].
     * @param value   Значение переменной, сохраняются только первые 4096 байта.
     * @param user_id Id пользователя, переменная которого устанавливается, в случае если данные запрашиваются серверным методом.
     * @param global  Указывается true, если необходимо работать с глобальными переменными, а не с переменными пользователя.
     * @return Возвращает 1 в случае удачного сохранения переменной.
     * Для удаления переменной необходимо передать пустое значение в параметре value.
     * @throws GlobalException
     */
    public JSONObject set(String key, String value, int user_id, boolean global) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"key", key},
                {"value", value},
                {"user_id", String.valueOf(user_id)},
                {"global", global ? "1" : "0"}
        });
    }

    /**
     * Возвращает названия всех переменных.
     * Задать значение позволяет метод storage.set.
     * Переменные могут храниться в двух областях видимости:
     * Пользовательская, переменная привязана к пользователю, другие пользователи не могут её прочитать.
     * Глобальная, переменная привязана к приложению, и работа с ней не зависит от пользователя.
     * Если метод вызывается не от имени приложения, то нужно передать параметр global для доступа к переменной.
     *
     * @param user_id Id пользователя, названия переменных которого получаются, в случае если данные запрашиваются серверным методом.
     * @param global  Указывается true, если необходимо работать с глобальными переменными, а не с переменными пользователя.
     * @param offset  Смещение, необходимое для выборки определенного подмножества названий переменных.
     * @param count   Количество названий переменных, информацию о которых необходимо получить.
     * @return Возвращает массив названий переменных.
     * @throws GlobalException
     */
    public JSONObject getKeys(int user_id, boolean global, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"global", global ? "1" : "0"},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
        });
    }
}
