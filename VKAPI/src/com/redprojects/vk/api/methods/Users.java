package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import static com.redprojects.vk.api.VKAPI.getResponseWithoutAuthorization;

@SuppressWarnings("UnusedDeclaration")
public class Users extends Method {

    public enum Fields {
        sex, bdate, city, country, photo_50, photo_100, photo_200_orig, photo_200, photo_400_orig,
        photo_max, photo_max_orig, online, online_mobile, lists, domain, has_mobile, contacts, connections,
        site, education, universities, schools, can_post, can_see_all_posts, can_see_audio,
        can_write_private_message, status, last_seen, common_count, relation, relatives, counters, screen_name,
        maiden_name, timezone, occupation, none
    }

    public enum NameCase {
        nom, gen, dat, acc, ins, abl
    }

    public enum ReportType {
        porn, spam, insult, advertisment
    }

    /**
     * Конструктор для вызова методов без авторизации
     */
    public Users() {
        super(null);
    }

    /**
     * Конструктор для вызова методов с авторизацией
     */
    public Users(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Возвращает расширенную информацию о пользователях.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param userIds  Перечисленные через запятую идентификаторы пользователей или их короткие имена (screen_name). По умолчанию — идентификатор текущего пользователя.
     *                 Список строк, разделенных через запятую, количество элементов должно составлять не более 1000
     * @param fields   Список дополнительных полей, которые необходимо вернуть.
     *                 Доступные значения содержатся в enum Fields
     *                 Список строк, разделенных через запятую
     * @param nameCase Падеж для склонения имени и фамилии пользователя.
     *                 Возможные значения: именительный – nom, родительный – gen, дательный – dat, винительный – acc, творительный – ins, предложный – abl.
     *                 Указаны в enum NameCase. По умолчанию nom.
     * @return После успешного выполнения возвращает массив объектов пользователей.
     * Поле counters будет возвращено только в случае, если передан ровно один user_id.
     */
    public JSONObject get(String[] userIds, Fields[] fields, NameCase nameCase) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";
        String bufUserIds = "";

        for (String userId : userIds)
            bufUserIds += userId + ",";

        for (Users.Fields userField : fields)
            bufFields += userField.name() + ",";

        String[][] request = new String[][]{
                {"user_ids", bufUserIds},
                {"fields", bufFields},
                {"name_case", nameCase.name()}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Возвращает список пользователей в соответствии с заданным критерием поиска.
     * Этот метод не требует прав доступа.
     *
     * @param q                  Строка поискового запроса.
     * @param sort               Сортировка результатов: 1 - по дате регистрации, 0 - по популярности
     * @param offset             Смещение относительно первого найденного пользователя для выборки определенного подмножества.
     *                           Положительное число
     * @param count              Количество возвращаемых пользователей.
     *                           Обратите внимание — даже при использовании параметра offset для получения информации доступны только первые 1000 результатов.
     *                           Положительное число, по умолчанию 20, максимальное значение 1000
     * @param fields             Список дополнительных полей, которые необходимо вернуть.
     * @param city               Идентификатор города.
     *                           Положительное число
     * @param country            Идентификатор страны.
     *                           Положительное число
     * @param hometown           Название города строкой.
     * @param university_country Идентификатор страны, в которой пользователи закончили ВУЗ.
     *                           Положительное число
     * @param university         Идентификатор ВУЗа.
     *                           Положительное число
     * @param university_year    Год окончания ВУЗа.
     *                           Положительное число
     * @param university_faculty Идентификатор факультета.
     *                           Положительное число
     * @param university_chair   Идентификатор кафедры.
     *                           Положительное число
     * @param sex                Пол, 1 — женщина, 2 — мужчина, 0 (по умолчанию) — любой.
     *                           Положительное число
     * @param status             Семейное положение: 1 — Не женат, 2 — Встречается, 3 — Помолвлен, 4 — Женат, 7 — Влюблён, 5 — Всё сложно, 6 — В активном поиске.
     *                           Положительное число
     * @param age_from           Начиная с какого возраста.
     *                           Положительное число
     * @param age_to             До какого возраста.
     *                           Положительное число
     * @param birth_day          День рождения.
     *                           Положительное число
     * @param birth_month        Месяц рождения.
     *                           Положительное число
     * @param birth_year         Год рождения.
     *                           Положительное число
     * @param online             1 — только в сети, 0 — все пользователи.
     *                           Флаг, может принимать значения 1 или 0
     * @param has_photo          1 — только с фотографией, 0 — все пользователи.
     *                           Флаг, может принимать значения 1 или 0
     * @param school_country     Идентификатор страны, в которой пользователи закончили школу.
     *                           Положительное число
     * @param school_city        Идентификатор города, в котором пользователи закончили школу.
     *                           Положительное число
     * @param school_class       Положительное число
     * @param school             Идентификатор школы, которую закончили пользователи.
     *                           Положительное число
     * @param school_year        Год окончания школы.
     *                           Положительное число
     * @param religion           Религиозные взгляды.
     * @param interests          Интересы.
     * @param company            Название компании, в которой работают пользователи.
     * @param position           Название должности.
     * @param group_id           Идентификатор группы, среди пользователей которой необходимо проводить поиск.
     *                           Положительное число
     * @return После успешного выполнения возвращает список объектов пользователей, найденных в соответствии с заданными критериями.
     */
    public JSONObject search(
            String q, int sort, int offset, int count, Fields[] fields,
            int city, int country, String hometown, int university_country,
            int university, int university_year, int university_faculty,
            int university_chair, int sex, int status, int age_from, int age_to,
            int birth_day, int birth_month, int birth_year, int online, int has_photo,
            int school_country, int school_city, int school_class, int school,
            int school_year, String religion, String interests, String company,
            String position, int group_id
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";

        for (Users.Fields userField : fields) {
            bufFields += userField.name() + ",";
        }

        String[][] request = new String[][]{
                {"q", q},
                {"sort", String.valueOf(sort)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"fields", bufFields},
                {"city", String.valueOf(city)},
                {"country", String.valueOf(country)},
                {"hometown", hometown},
                {"university_country", String.valueOf(university_country)},
                {"university", String.valueOf(university)},
                {"university_year", String.valueOf(university_year)},
                {"university_faculty", String.valueOf(university_faculty)},
                {"university_chair", String.valueOf(university_chair)},
                {"sex", String.valueOf(sex)},
                {"status", String.valueOf(status)},
                {"age_from", String.valueOf(age_from)},
                {"age_to", String.valueOf(age_to)},
                {"birth_day", String.valueOf(birth_day)},
                {"birth_month", String.valueOf(birth_month)},
                {"birth_year", String.valueOf(birth_year)},
                {"online", String.valueOf(online)},
                {"has_photo", String.valueOf(has_photo)},
                {"school_country", String.valueOf(school_country)},
                {"school_city", String.valueOf(school_city)},
                {"school_class", String.valueOf(school_class)},
                {"school", String.valueOf(school)},
                {"school_year", String.valueOf(school_year)},
                {"religion", religion},
                {"interests", interests},
                {"company", company},
                {"position", position},
                {"group_id", String.valueOf(group_id)}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }
        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Возвращает информацию о том, установил ли пользователь приложение.
     * Этот метод не требует прав доступа.
     *
     * @param user_id Идентификатор пользователя.
     *                По умолчанию идентификатор текущего пользователя
     * @return После успешного выполнения возвращает 1 в случае, если пользователь установил у себя данное приложение, иначе 0.
     */
    public JSONObject isAppUser(int user_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"user_id", String.valueOf(user_id)}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Возвращает список идентификаторов пользователей и групп, которые входят в список подписок пользователя.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param user_id  Идентификатор пользователя, подписки которого необходимо получить.
     *                 По умолчанию идентификатор текущего пользователя
     * @param extended 1 – возвращает объединенный список, содержащий объекты групп и пользователей. 0 – возвращает список идентификаторов групп и пользователей отдельно. (по умолчанию)
     *                 Флаг, может принимать значения 1 или 0
     * @param offset   Смещение необходимое для выборки определенного подмножества подписок. Этот параметр используется только если передан extended=1.
     *                 Положительное число
     * @param count    Количество подписок, которые необходимо вернуть. Этот параметр используется только если передан extended=1.
     *                 Положительное число, по умолчанию 20, максимальное значение 200
     * @param fields   Список дополнительных полей, которые необходимо вернуть.
     *                 Список строк, разделенных через запятую
     * @return После успешного выполнения возвращает объекты users и groups, каждый из которых содержит поле count — количество результатов и items — список идентификаторов пользователей или публичных страниц, на которые подписан текущий пользователь (из раздела «Интересные страницы»).
     * Если был задан параметр extended=1, возвращает объединенный список объектов сообществ и пользователей.
     */
    public JSONObject getSubscriptions(int user_id, int extended, int offset, int count, Fields[] fields) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";

        for (Users.Fields userField : fields) {
            bufFields += userField.name() + ",";
        }

        String[][] request = new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"extended", String.valueOf(extended)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"fields", bufFields}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Возвращает список идентификаторов пользователей, которые являются подписчиками пользователя. Идентификаторы пользователей в списке отсортированы в порядке убывания времени их добавления.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param user_id   Идентификатор пользователя.
     *                  Положительное число, по умолчанию идентификатор текущего пользователя
     * @param offset    Смещение, необходимое для выборки определенного подмножества подписчиков.
     *                  Положительное число
     * @param count     Количество подписчиков, информацию о которых нужно получить.
     *                  Положительное число, по умолчанию 100, максимальное значение 1000
     * @param fields    Список дополнительных полей, которые необходимо вернуть.
     *                  Доступные значения указаны в enum Fields
     * @param name_case Падеж для склонения имени и фамилии пользователя. Возможные значения: именительный – nom, родительный – gen, дательный – dat, винительный – acc, творительный – ins, предложный – abl.
     *                  Указаны в enum NameCase. По умолчанию nom.
     * @return После успешного выполнения возвращает список объектов пользователей, которые являются подписчиками пользователя uid.
     */
    public JSONObject getFollowers(int user_id, int offset, int count, Fields[] fields, NameCase name_case) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";

        for (Users.Fields userField : fields) {
            bufFields += userField.name() + ",";
        }

        String[][] request = new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"fields", bufFields},
                {"name_case", name_case.name()}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Позволяет пожаловаться на пользователя.
     * Данный метод доступен только Standalone-приложениям.
     *
     * @param user_id Идентификатор пользователя, на которого нужно подать жалобу.
     *                Положительное число, ''''обязательный параметр''''
     * @param type    Тип жалобы, может принимать следующие значения:
     *                porn — порнография;
     *                spam — рассылка спама;
     *                insult — оскорбительное поведение;
     *                advertisment — рекламная страница, засоряющая поиск.
     *                Указаны в enum ReportType.
     *                ''''обязательный параметр''''
     * @param comment комментарий к жалобе на пользователя.
     * @return В случае успешной отправки жалобы метод вернет 1.
     */
    public JSONObject report(int user_id, ReportType type, String comment) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"type", type.name()},
                {"comment", comment}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }

    /**
     * Индексирует текущее местоположение пользователя и возвращает список пользователей, которые находится в близи.
     * Данный метод доступен только Standalone-приложениям.
     *
     * @param latitude  Географическая широта точки, в которой в данный момент находится пользователь, заданная в градусах (от -90 до 90).
     *                  Дробное число, ''''обязательный параметр''''
     * @param longitude Географическая долгота точки, в которой в данный момент находится пользователь, заданная в градусах (от -180 до 180).
     *                  Дробное число, ''''обязательный параметр''''
     * @param accuracy  Точность текущего местоположения пользователя в метрах.
     *                  Положительное число
     * @param timeout   Время в секундах через которое пользователь должен перестать находиться через поиск по местоположению.
     *                  Положительное число, по умолчанию 7200
     * @param radius    Тип радиуса зоны поиска (от 1 до 4)
     *                  1 — 300 метров;
     *                  2 — 2400 метров;
     *                  3 — 18 километров;
     *                  4 — 150 километров.
     *                  Положительное число, по умолчанию 1
     * @param fields    Список дополнительных полей, которые необходимо вернуть.
     *                  Доступные значения указаны в enum Fields
     * @param name_case Падеж для склонения имени и фамилии пользователя. Возможные значения: именительный – nom, родительный – gen, дательный – dat, винительный – acc, творительный – ins, предложный – abl.
     *                  Указаны в enum NameCase. По умолчанию nom.
     * @return Возвращает список пользователей, которые находится в близи.
     */
    public JSONObject getNearby(float latitude, float longitude, int accuracy, int timeout, int radius, Fields[] fields, NameCase name_case) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";

        for (Users.Fields userField : fields) {
            bufFields += userField.name() + ",";
        }

        String[][] request = new String[][]{
                {"latitude", String.valueOf(latitude)},
                {"longitude", String.valueOf(longitude)},
                {"accuracy", String.valueOf(accuracy)},
                {"timeout", String.valueOf(timeout)},
                {"radius", String.valueOf(radius)},
                {"fields", bufFields},
                {"name_case", name_case.name()}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }
}
