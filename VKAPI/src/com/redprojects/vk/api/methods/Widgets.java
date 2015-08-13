package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import static com.redprojects.vk.api.VKAPI.getResponseWithoutAuthorization;

@SuppressWarnings("UnusedDeclaration")
public class Widgets extends Method {

    public enum SortType {
        date, likes, last_comment
    }

    public enum PeriodType {
        day, week, month, alltime
    }

    public Widgets() {
        super(null);
    }

    public Widgets(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Получает список комментариев к странице, оставленных через Виджет комментариев.
     *
     * @param widget_api_id Идентификатор приложения/сайта, с которым инициализируются виджеты.
     * @param url           URL-адрес страницы.
     * @param page_id       Внутренний идентификатор страницы в приложении/сайте (в случае, если для инициализации виджетов использовался параметр page_id).
     * @param order         Тип сортировки комментариев. Возможные значения содержатся в enum SortType.
     * @param fields        Перечисленные через запятую поля анкет необходимые для получения. Если среди полей присутствует replies, будут возвращены последние комментарии второго уровня для каждого комментария первого уровня.
     * @param offset        Смещение необходимое для выборки определенного подмножества комментариев. По умолчанию 0.
     *                      Положительное число
     * @param count         Количество возвращаемых записей.
     *                      Положительное число, по умолчанию 10, максимальное значение 200
     * @return В случае успеха возвращает объект со следующими полями:
     * count — общее количество комментариев первого уровня к странице (без учета ограничений из входного параметра count).
     * posts — список комментариев первого уровня. Каждый элемент списка имеет структуру, схожую с объектами post из результатов метода wall.message. Помимо этого:
     * В случае, если среди необходимых полей было указано replies, в поле comments.replies будет содержаться список комментариев второго уровня. Каждый элемент этого списка имеет структуру, схожую с объектами comment из результатов метода wall.getComments.
     * В случае, если были указаны любые другие необходимые поля анкет, в каждом элементе post и comment будет поле user, содержащее соответствующую информацию об авторе комментария.
     */
    public JSONObject getComments(
            int widget_api_id, String url, String page_id, SortType order,
            String[] fields, int offset, int count
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufFields = "";
        for (String field : fields) {
            bufFields += field + ",";
        }
        String[][] request = new String[][]{
                {"widget_api_id", String.valueOf(widget_api_id)},
                {"url", url},
                {"page_id", page_id},
                {"order", order.name()},
                {"fields", bufFields},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        };

        return vkapi != null ? vkapi.getResponse(name + "." + methodName, request) : getResponseWithoutAuthorization(name + "." + methodName, request);

    }

    /**
     * Получает список страниц приложения/сайта, на которых установлен Виджет комментариев или «Мне нравится».
     *
     * @param widget_api_id идентификатор приложения/сайта, с которым инициализируются виджеты.
     * @param order         Тип сортировки комментариев. Возможные значения содержатся в enum SortType.
     * @param period        Период выборки. Возможные значения содержатся в enum PeriudType
     * @param offset        Смещение необходимое для выборки определенного подмножества комментариев. По умолчанию 0.
     *                      Положительное число
     * @param count         Количество возвращаемых записей.
     *                      Положительное число, по умолчанию 10, максимальное значение 200
     * @return В случае успеха возвращает объект со следующими полями:<br />
     * count — общее количество страниц (без учета ограничений входного параметра count)<br />
     * pages — список объектов-страниц<br />
     * Каждый объект, описывающий страницу, имеет следующие поля:<br />
     * id — идентификатор страницы в системе;<br />
     * title — заголовок страницы (берется из мета-тегов на странице или задается параметром pageTitle при инициализации)<br />
     * description — краткое описание страницы (берется из мета-тегов на странице или задается параметром pageDescription при инициализации);<br />
     * photo — объект, содержащий фотографию-миниатюру страницы (берется из мета-тегов на странице или задается параметром pageImage при инициализации)<br />
     * url — абсолютный адрес страницы;<br />
     * likes — объект, содержащий поле count — количество отметок «Мне нравится» к странице. Для получения списка пользователей, отметивших страницу можно использовать метод likes.getList с параметром type равным site_page;<br />
     * comments — объект, содержащий поле count — количество комментариев к странице внутри виджета. Для получения списка комментариев можно использовать метод widgets.getComments;<br />
     * date — дата первого обращения к виджетам на странице<br />
     * page_id — внутренний идентификатор страницы в приложении/на сайте (в случае, если при инициализации виджетов использовался параметр page_id);<br />
     */
    public JSONObject getPages(int widget_api_id, SortType order, PeriodType period, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"widget_api_id", String.valueOf(widget_api_id)},
                {"order", order.name()},
                {"period", period.name()},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        };

        if (vkapi != null) {
            return vkapi.getResponse(name + "." + methodName, request);
        }

        return getResponseWithoutAuthorization(name + "." + methodName, request);
    }
}
