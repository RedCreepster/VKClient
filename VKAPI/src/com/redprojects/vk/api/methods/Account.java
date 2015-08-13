package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.AccountException;
import com.redprojects.vk.api.exceptions.GlobalException;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class Account extends Method {

    public enum Filter {
        friends, messages, photos,
        videos, notes, gifts, events,
        groups, notifications, sdk
    }

    public enum Service {
        email, phone, twitter,
        facebook, odnoklassniki, instagram,
        google
    }

    public enum LookupFields {
        nickname, domain, sex,
        bdate, city, country,
        timezone, photo_50, photo_100,
        photo_200_orig, has_mobile, contacts,
        education, online, relation,
        last_seen, status, can_write_private_message,
        can_see_all_posts, can_post, universities
    }

    public enum Subscribes {
        msg, friend, call, reply, mention, group, like
    }

    public Account(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Возвращает ненулевые значения счетчиков пользователя.
     *
     * @param filters Счетчики, информацию о которых нужно вернуть
     * @return Возвращает объект, который может содержать поля Filter
     * @throws GlobalException
     */
    public JSONObject getCounters(Filter[] filters) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFilters = "";
        for (Filter filter : filters)
            bufFilters += filter.name() + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"filters", bufFilters}
        });
    }

    /**
     * Устанавливает короткое название приложения(до 17 символов), которое выводится пользователю в левом меню.
     * Это происходит только в том случае, если пользователь добавил приложение в левое меню со страницы приложения, списка приложений или настроек.
     *
     * @param userId Идентификатор пользователя.
     * @param name   Короткое название приложения.
     * @return Возвращает 1 в случае успешной установки короткого названия.
     * Если пользователь не установил приложение в левое меню, метод вернет ошибку 148 (Access to the menu of the user denied).
     * Избежать этой ошибки можно с помощью метода account.getAppPermissions.
     * @throws AccountException 148
     * @throws GlobalException
     */
    public JSONObject setNameInMenu(int userId, String name) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(userId)},
                {"name", name}
        });
    }

    /**
     * Помечает текущего пользователя как online на 15 минут.
     *
     * @param voip Возможны ли видеозвонки для данного устройства
     * @return В случае успешного выполнения метода будет возвращён код 1.
     * @throws GlobalException
     */
    public JSONObject setOnline(boolean voip) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"voip", voip ? "1" : "0"}
        });
    }

    /**
     * Помечает текущего пользователя как offline.
     *
     * @return В случае успешного выполнения метода будет возвращён код 1.
     * @throws GlobalException
     */
    public JSONObject setOffline() throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[0][]);
    }

    /**
     * Позволяет искать пользователей ВКонтакте, используя телефонные номера, email-адреса, и идентификаторы пользователей в других сервисах.
     * Найденные пользователи могут быть также в дальнейшем получены методом friends.getSuggestions.
     * Для вызова этого метода Ваше приложение должно иметь права: friends.
     *
     * @param contacts  Список контактов.
     * @param service   Строковой идентификатор сервиса, по контактам которого производится поиск.
     * @param myContact Контакт текущего пользователя в заданном сервисе.
     * @param returnAll true – возвращать также контакты, найденные ранее с использованием этого сервиса, false – возвращать только контакты, найденные с использованием поля contacts.
     * @param fields    Список дополнительных полей, которые необходимо вернуть.
     * @return В качестве результата метод возвращает два списка:
     * found – список объектов пользователей, расширенных полями contact – контакт, по которому был найден пользователь (не приходит если пользователь был найден при предыдущем использовании метода),
     * request_sent – запрос на добавление в друзья уже был выслан, либо пользователь уже является другом,
     * common_count если этот контакт также был импортирован друзьями или контактами текущего пользователя.
     * Метод также возвращает найденные ранее контакты.
     * other – список контактов, которые не были найдены. Объект содержит поля contact и common_count если этот контакт также был импортирован друзьями или контактами текущего пользователя.
     * @throws GlobalException
     */
    public JSONObject lookupContacts(
            String[] contacts, Service service, String myContact, boolean returnAll, LookupFields[] fields
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufContacts = "";
        for (String contact : contacts)
            bufContacts += contact + ",";

        String bufFields = "";
        for (LookupFields field : fields)
            bufFields += field.name() + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"contacts", bufContacts},
                {"service", service.name()},
                {"mycontact", myContact},
                {"return_all", returnAll ? "1" : "0"},
                {"fields", bufFields}
        });
    }

    /**
     * Подписывает устройство на базе iOS, Android или Windows Phone на получение Push-уведомлений.
     * Для вызова этого метода Ваше приложение должно иметь права: messages.
     *
     * @param token         Идентификатор устройства, используемый для отправки уведомлений. (для mpns идентификатор должен представлять из себя URL для отправки уведомлений)
     * @param deviceModel   Строковое название модели устройства.
     * @param deviceId      Уникальный идентификатор устройства.
     * @param systemVersion Строковая версия операционной системы устройства.
     * @param noText        true — не передавать текст сообщения в push уведомлении. false — текст сообщения передаётся.
     * @param subscribes    Список типов уведомлений, которые следует присылать.
     * @return Возвращает 1 в случае успешного выполнения метода.
     * На iOS и Windows Phone push-уведомления будут отображены без какой либо обработки.
     * На Android будут приходить события в <a href="https://vk.com/dev/android_push">следующем формате</a>.
     * @throws GlobalException
     */
    public JSONObject registerDevice(
            String token, String deviceModel, String deviceId, String systemVersion,
            boolean noText, Subscribes[] subscribes
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufSubscribes = "";
        for (Subscribes subscribe : subscribes)
            bufSubscribes += subscribe.name() + ",";

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"token", token},
                {"device_model", deviceModel},
                {"device_id", deviceId},
                {"system_version", systemVersion},
                {"no_text", noText ? "1" : "0"},
                {"subscribe", bufSubscribes}
        });
    }

    /**
     * Отписывает устройство от Push уведомлений.
     * Для вызова этого метода Ваше приложение должно иметь права: messages.
     *
     * @param token Идентификатор устройства, используемый для отправки уведомлений. (для mpns идентификатор должен представлять из себя URL для отправки уведомлений)
     * @return Возвращает 1 в случае успешного выполнения метода.
     * @throws GlobalException
     */
    public JSONObject unregisterDevice(String token) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"token", token}
        });
    }

    /**
     * Отключает push-уведомления на заданный промежуток времени.
     * Для вызова этого метода Ваше приложение должно иметь права: messages.
     *
     * @param token  Идентификатор устройства для сервиса push уведомлений.
     * @param time   Время в секундах на которое требуется отключить уведомления, -1 отключить навсегда
     * @param chatId Идентификатор чата, для которого следует отключить уведомления
     * @param userId Идентификатор пользователя, для которого следует отключить уведомления
     * @param sound  true - включить звук в данном диалоге, false - отключить звук (параметр работает только если указан chat_id или user_id)
     * @return Возвращает 1 в случае успешного выполнения метода.
     * @throws GlobalException
     */
    public JSONObject setSilenceMode(String token, int time, int chatId, int userId, boolean sound) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"token", token},
                {"time", String.valueOf(time)},
                {"chat_id", String.valueOf(chatId)},
                {"user_id", String.valueOf(userId)},
                {"sound", sound ? "1" : "0"}
        });
    }

    /**
     * Позволяет получать настройки Push уведомлений.
     * Для вызова этого метода Ваше приложение должно иметь права: messages.
     *
     * @param token Идентификатор устройства, используемый для отправки уведомлений. (для mpns идентификатор должен представлять из себя URL для отправки уведомлений)
     * @return Возвращает объект, содержащий поля:
     * disabled — отключены ли уведомления.
     * disabled_until — unixtime-значение времени, до которого временно отключены уведомления.
     * conversations — список, содержащий настройки конкретных диалогов, и их количество первым элементом.
     * @throws GlobalException
     */
    public JSONObject getPushSettings(String token) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"token", token}
        });
    }

    /**
     * Получает настройки текущего пользователя в данном приложении.
     *
     * @param userId Идентификатор пользователя, информацию о настройках которого необходимо получить.
     * @return После успешного выполнения возвращает битовую маску настроек текущего пользователя в данном приложении.
     * @throws GlobalException
     */
    public JSONObject getAppPermissions(int userId) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(userId)}
        });
    }

    /**
     * Возвращает список активных рекламных предложений (офферов), выполнив которые пользователь сможет получить соответствующее количество голосов на свой счёт внутри приложения.
     * Для отображения пользователю одного или всех рекламных предложений можно использовать метод JS API showOrderBox.
     * После выполнения пользователем одной из предложенных ему акций, голоса будут зачислены на его счёт внутри приложения автоматически.
     * В этом случае приложение может сразу списать необходимое количество голосов, сконвертировав голоса во внутреннюю валюту приложения.
     *
     * @param offset Смещение, необходимое для выборки определенного подмножества офферов.
     * @param count  Количество офферов, которое необходимо получить
     * @return Возвращает массив, состоящий из общего количества старгетированных на текущего пользователя специальных предложений (первый элемент), и списка объектов с информацией о предложениях.
     * В случае, если на пользователя не старгетировано ни одного специального предложения, массив будет содержать элемент 0 (количество специальных предложений).
     * @throws GlobalException
     */
    public JSONObject getActiveOffers(int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Добавляет пользователя в черный список.
     * Если указанный пользователь является другом текущего пользователя или имеет от него входящую или исходящую заявку в друзья,
     * то для добавления пользователя в черный список Ваше приложение должно иметь права: friends.
     *
     * @param userId Идентификатор пользователя, которого нужно добавить в черный список.
     * @return В случае успеха метод вернет 1.
     * @throws GlobalException
     */
    public JSONObject banUser(int userId) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(userId)}
        });
    }

    /**
     * Убирает пользователя из черного списка.
     *
     * @param userId Идентификатор пользователя, которого нужно убрать из черного списка.
     * @return В случае успеха метод вернет 1.
     * @throws GlobalException
     */
    public JSONObject unbanUser(int userId) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"user_id", String.valueOf(userId)}
        });
    }

    /**
     * Возвращает список пользователей, находящихся в черном списке.
     *
     * @param offset Смещение необходимое для выборки определенного подмножества черного списка.
     * @param count  Количество записей, которое необходимо вернуть.
     * @return Возвращает набор объектов пользователей, находящихся в черном списке.
     * @throws GlobalException
     */
    public JSONObject getBanned(int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        });
    }

    /**
     * Возвращает информацию о текущем аккаунте.
     *
     * @return Метод возвращает объект, содержащий следующие поля:
     * country – строковой код страны, определенный по IP адресу, с которого сделан запрос.
     * https_required – 1 - пользователь установил на сайте настройку "Всегда использовать безопасное соединение"; 0 - безопасное соединение не требуется.
     * intro – битовая маска отвечающая за прохождение обучения использованию приложения.
     * lang – числовой идентификатор текущего языка пользователя.
     * @throws GlobalException
     */
    public JSONObject getInfo() throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[0][]);
    }

    /**
     * Позволяет редактировать информацию о текущем аккаунте.
     *
     * @param intro Битовая маска, отвечающая за прохождение обучения в мобильных клиентах.
     * @return
     * @throws GlobalException
     */
    public JSONObject setInfo(int intro) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"intro", String.valueOf(intro)}
        });
    }

    /**
     * Позволяет сменить пароль пользователя после успешного восстановления доступа к аккаунту через СМС, используя метод auth.restore.
     *
     * @param restoreSid         Идентификатор сессии, полученный при восстановлении доступа используя метод auth.restore.
     *                           (В случае если пароль меняется сразу после восстановления доступа)
     * @param changePasswordHash Хэш, полученный при успешной OAuth авторизации по коду полученному по СМС
     *                           (В случае если пароль меняется сразу после восстановления доступа)
     * @param oldPassword        Текущий пароль пользователя.
     * @param newPassword        Новый пароль, который будет установлен в качестве текущего.
     * @return В результате выполнения этого метода будет возвращем объект с полем token, содержащим новый токен, и полем secret в случае, если токен был nohttps.
     * @throws GlobalException
     */
    public JSONObject changePassword(String restoreSid, String changePasswordHash, String oldPassword, String newPassword) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"restore_sid", restoreSid},
                {"change_password_hash", changePasswordHash},
                {"old_password", oldPassword},
                {"new_password", newPassword}
        });
    }

    /**
     * Возвращает информацию о текущем профиле.
     * Для получения информации о профиле другого пользователя или для работы без авторизации используйте users.get.
     *
     * @return Метод возвращает объект, содержащий следующие поля:
     * first_name – имя пользователя;
     * last_name – фамилия пользователя;
     * maiden_name – девичья фамилия пользователя (только для женского пола);
     * sex – пол пользователя, возвращаемые значения:
     * 1 — женский;
     * 2 — мужской;
     * 0 — пол не указан;
     * relation – семейное положение пользователя, возвращаемые значения:
     * 1 — не женат/не замужем;
     * 2 — есть друг/есть подруга;
     * 3 — помолвлен/помолвлена;
     * 4 — женат/замужем;
     * 5 — всё сложно;
     * 6 — в активном поиске;
     * 7 — влюблён/влюблена;
     * 0 — не указано;
     * relation_partner – объект пользователя, с которым связано семейное положение (если есть);
     * bdate – дата рождения пользователя, возвращается в формате DD.MM.YYYY;
     * bdate_visibility – видимость даты рождения, возвращаемые значения:
     * 1 — показывать дату рождения;
     * 2 — показывать только месяц и день;
     * 0 — не показывать дату рождения;
     * home_town – родной город пользователя;
     * country – страна пользователя;
     * city – город пользователя;
     * name_request – объект, содержащий информацию о заявке на смену имени, если она была подана, со следующими полями:
     * id – идентификатор заявки, необходимый для её отмены (только если status равен processing);
     * status – статус заявки, возвращаемые значения:
     * processing – заявка рассматривается;
     * declined – заявка отклонена;
     * first_name – имя пользователя, указанное в заявке;
     * last_name – фамилия пользователя, указанная в заявке.
     * @throws GlobalException
     */
    public JSONObject getProfileInfo() throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[0][]);
    }

    /**
     * Редактирует информацию текущего профиля.
     *
     * @param firstName         Имя пользователя.
     * @param lastName          Фамилия пользователя.
     * @param maidenName        Девичья фамилия пользователя (только для женского пола).
     * @param cancelRequestId   Идентификатор заявки на смену имени, которую необходимо отменить.
     *                          Если передан этот параметр, все остальные параметры игнорируются.
     * @param sex               Пол пользователя. Возможные значения:
     *                          1 — женский;
     *                          2 — мужской.
     * @param relation          Семейное положение пользователя. Возможные значения:
     *                          1 — не женат/не замужем;
     *                          2 — есть друг/есть подруга;
     *                          3 — помолвлен/помолвлена;
     *                          4 — женат/замужем;
     *                          5 — всё сложно;
     *                          6 — в активном поиске;
     *                          7 — влюблён/влюблена;
     *                          0 — не указано.
     * @param relationPartnerId Идентификатор пользователя, с которым связано семейное положение.
     * @param bdate             Дата рождения пользователя в формате DD.MM.YYYY, например "15.11.1984".
     * @param bdateVisibility   Видимость даты рождения. Возможные значения:
     *                          1 — показывать дату рождения;
     *                          2 — показывать только месяц и день;
     *                          0 — не показывать дату рождения.
     * @param homeTown          Родной город пользователя.
     * @param countryId         Идентификатор страны пользователя.
     * @param cityId            Идентификатор города пользователя.
     * @return Метод возвращает объект, содержащий следующие поля:
     * changed – возвращает 1 — если информация была сохранена, 0 — если ни одно из полей не было сохранено.
     * Если в параметрах передавалось имя или фамилия пользователя, в объекте также будет возвращено поле name_request – объект, содержащий информацию о заявке на смену имени, со следующими полями:
     * id – идентификатор заявки, необходимый для её отмены (только если status равен processing);
     * status – статус заявки, возвращаемые значения:
     * success – имя было успешно изменено;
     * processing – заявка рассматривается;
     * declined – заявка отклонена;
     * was_accepted – недавно уже была одобрена заявка, повторную заявку можно подать не раньше даты, указанной в поле repeat_date;
     * was_declined – предыдущая заявка была отклонена, повторную заявку можно подать не раньше даты, указанной в поле repeat_date;
     * first_name – имя пользователя, указанное в заявке;
     * last_name – фамилия пользователя, указанная в заявке.
     * @throws GlobalException
     */
    public JSONObject saveProfileInfo(
            String firstName, String lastName, String maidenName, int cancelRequestId, int sex, int relation,
            int relationPartnerId, String bdate, int bdateVisibility, String homeTown, int countryId, int cityId
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"first_name", firstName},
                {"last_name", lastName},
                {"maiden_name", maidenName},
                {"cancel_request_id", String.valueOf(cancelRequestId)},
                {"sex", String.valueOf(sex)},
                {"relation", String.valueOf(relation)},
                {"relation_partner_id", String.valueOf(relationPartnerId)},
                {"bdate", bdate},
                {"bdate_visibility", String.valueOf(bdateVisibility)},
                {"home_town", homeTown},
                {"country_id", String.valueOf(countryId)},
                {"city_id", String.valueOf(cityId)}
        });
    }
}
