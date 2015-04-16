package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.VKAPI;
import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

/**
 * Для вызова этих методов Ваше приложение должно иметь следующие права: messages.
 */
@SuppressWarnings("UnusedDeclaration")
public class Messages extends Method {

    public Messages(VKAPI vkapi) {
        super(vkapi);
    }

    /**
     * Возвращает список входящих либо исходящих личных сообщений текущего пользователя.
     *
     * @param out             Если этот параметр равен 1, сервер вернет исходящие сообщения.
     * @param offset          Смещение, необходимое для выборки определенного подмножества сообщений
     *                        Положительное число
     * @param count           Количество сообщений, которое необходимо получить.
     *                        Положительное число, максимальное значение 200
     * @param time_offset     Максимальное время, прошедшее с момента отправки сообщения до текущего момента в секундах. 0, если Вы хотите получить сообщения любой давности.
     * @param filters         Фильтр возвращаемых сообщений: 8 — важные сообщения.
     * @param preview_length  Количество символов, по которому нужно обрезать сообщение. Укажите 0, если Вы не хотите обрезать сообщение. (по умолчанию сообщения не обрезаются). Обратите внимание что сообщения обрезаются по словам.
     *                        Положительное число
     * @param last_message_id Идентификатор сообщения, полученного перед тем, которое нужно вернуть последним (при условии, что после него было получено не более count сообщений, иначе необходимо использовать с параметром offset).
     *                        Положительное число
     * @return После успешного выполнения возвращает список объектов сообщений.
     */
    public JSONObject get(
            int out, int offset, int count, int time_offset,
            int filters, int preview_length, int last_message_id
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"out", String.valueOf(out)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"time_offset", String.valueOf(time_offset)},
                {"filters", String.valueOf(filters)},
                {"preview_length", String.valueOf(preview_length)},
                {"last_message_id", String.valueOf(last_message_id)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject getDialogs(int offset, int count, int preview_length, int unread) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"preview_length", String.valueOf(preview_length)},
                {"unread", String.valueOf(unread)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject getById(int[] message_ids, int preview_length) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufIds = "";
        for (int id : message_ids)
            bufIds += String.valueOf(id) + ",";

        String[][] request = new String[][]{
                {"message_ids", bufIds},
                {"preview_length", String.valueOf(preview_length)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject search(String q, int preview_length, int offset, int count) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"q", q},
                {"preview_length", String.valueOf(preview_length)},
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    /**
     * Возвращает историю сообщений для указанного пользователя.
     *
     * @param offset           Смещение, необходимое для выборки определенного подмножества сообщений, должен быть >= 0, если не передан параметр start_message_id, и должен быть <= 0, если передан.
     * @param count            Количество сообщений, которое необходимо получить (но не более 200).
     *                         Положительное число, по умолчанию 20, максимальное значение 200
     * @param user_id          Идентификатор пользователя, историю переписки с которым необходимо вернуть.
     *                         ''''обязательный параметр''''
     * @param chat_id          Идентификатор диалога, историю сообщений которого необходимо получить.
     *                         Положительное число
     * @param start_message_id Если значение > 0, то это идентификатор сообщения, начиная с которого нужно вернуть историю переписки, если же передано значение -1, то к значению параметра offset прибавляется количество входящих непрочитанных сообщений в конце диалога (подробности см. ниже)
     * @param rev              1 – возвращать сообщения в хронологическом порядке. 0 – возвращать сообщения в обратном хронологическом порядке (по умолчанию), недоступен при переданном start_message_id.
     * @return После успешного выполнения возвращает список объектов сообщений.
     * <p/>
     * В случае наличия в этом диалоге непрочитанных входящих сообщений, дополнительно (после поля count, перед полем items) в список будет добавлено поле unread с количеством непрочитанных входящих сообщений.
     * <p/>
     * При переданном start_message_id в случае, если не было возвращено последнее сообщение в истории переписки (то есть сколько-то сообщений было пропущено), количество пропущенных сообщений (то есть реальное значение offset, которое использовалось для получения интервала истории) будет возвращено в поле skipped (вместе с count, items и иногда unread).
     * <p/>
     * <p/>
     * Параметр start_message_id вместе с offset <= 0 и count > 0 позволяет получить интервал истории сообщений вокруг данного сообщения или вокруг начала отрезка непрочитанных входящих сообщений.
     * <p/>
     * Для start_message_id > 0 к значению параметра offset прибавляется количество сообщений, чей идентификатор строго больше данного start_message_id (при offset равном 0 вернутся сообщения начиная с данного включительно и более старые, count штук).
     * <p/>
     * Для start_message_id = -1 поведение такое же, как при start_message_id равном последнему сообщению в истории переписки, не являющемуся входящим непрочитанным (при отсутствии входящих непрочитанных в этом диалоге это совпадает с отсутствием параметра start_message_id), то есть к значению offset прибавляется количество входящих непрочитанных сообщений в конце истории.
     */
    public JSONObject getHistory(
            int offset, int count, String user_id,
            int chat_id, int start_message_id, int rev
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String[][] request = new String[][]{
                {"offset", String.valueOf(offset)},
                {"count", String.valueOf(count)},
                {"user_id", String.valueOf(user_id)},
                {"chat_id", String.valueOf(chat_id)},
                {"start_message_id", String.valueOf(start_message_id)},
                {"rev", String.valueOf(rev)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject send(
            int user_id, String domain, int chat_id, int[] user_ids, String message,
            int guid, float lat, float _long, String attachment, int[] forward_messages
    ) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufUser_ids = "";
        for (int buser_id : user_ids) {
            bufUser_ids += String.valueOf(buser_id) + ",";
        }

        String bufForward_messages = "";
        for (int forward_message : forward_messages) {
            bufForward_messages += String.valueOf(forward_message) + ",";
        }

        String[][] request = new String[][]{
                {"user_id", String.valueOf(user_id)},
                {"domain", domain},
                {"chat_id", String.valueOf(chat_id)},
                {"user_ids", bufUser_ids},
                {"message", message},
                {"guid", String.valueOf(guid)},
                {"lat", String.valueOf(lat)},
                {"_long", String.valueOf(_long)},
                {"attachment", attachment},
                {"forward_messages", bufForward_messages}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject markAsRead(int[] message_ids, String peer_id, int start_message_id) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        String bufMessage_ids = "";
        for (int message_id : message_ids) {
            bufMessage_ids += String.valueOf(message_id) + ",";
        }

        String[][] request = new String[][]{
                {"message_ids", bufMessage_ids},
                {"peer_id", peer_id},
                {"start_message_id", String.valueOf(start_message_id)}
        };

        return vkapi.getResponse(name + "." + methodName, request);
    }

    public JSONObject getLongPollServer(boolean useSSL, boolean needPts) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return vkapi.getResponse(name + "." + methodName, new String[][]{
                {"use_ssl", useSSL ? "1" : "0"},
                {"need_pts", needPts ? "1" : "0"}
        });
    }
}
