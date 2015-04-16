package com.redprojects.vk.api.methods;

import com.redprojects.vk.api.exceptions.VKAPIException;
import org.json.JSONObject;

import static com.redprojects.vk.api.VKAPI.getResponseWithoutAuthorization;

public class Auth extends Method {

    public Auth() {
        super(null);
    }

    /**
     * Проверяет правильность введённого номера.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param phone         Номер телефона регистрируемого пользователя.
     *                      ''''обязательный параметр''''
     * @param client_id     Идентификатор Вашего приложения.
     * @param client_secret Секретный ключ приложения, доступный в разделе редактирования приложения.
     *                      ''''обязательный параметр''''
     * @return В случае, если номер пользователя является правильным, будет возвращён 1.
     * @throws VKAPIException 1000 Недопустимый номер телефона.
     * @throws VKAPIException 1004 Номер телефона занят другим пользователем.
     * @throws VKAPIException 1112 Обработка... Попробуйте позже.
     */
    public JSONObject checkPhone(String phone, int client_id, String client_secret) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponseWithoutAuthorization(name + "." + methodName, new String[][]{
                {"phone", phone},
                {"client_id", String.valueOf(client_id)},
                {"client_secret", client_secret}
        });
    }

    /**
     * Регистрирует нового пользователя по номеру телефона.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param first_name    Имя пользователя.
     *                      ''''обязательный параметр''''
     * @param last_name     Фамилия пользователя.
     *                      ''''обязательный параметр''''
     * @param client_id     Идентификатор Вашего приложения.
     *                      ''''обязательный параметр''''
     * @param client_secret Секретный ключ приложения, доступный в резделе редактирования приложения.
     *                      ''''обязательный параметр''''
     * @param phone         Номер телефона регистрируемого пользователя. Номер телефона может быть проверен заранее методом auth.checkPhone.
     *                      ''''обязательный параметр''''
     * @param password      Пароль пользователя, который будет использоваться при входе. Не меньше 6 символов. Также пароль может быть указан позже, при вызове метода auth.confirm.
     * @param test_mode     1 — тестовый режим, при котором не будет зарегистрирован новый пользователь, но при этом номер не будет проверяться на использованность. 0 — (по умолчанию) рабочий.
     *                      Флаг, может принимать значения 1 или 0
     * @param voice         1 — в случае, если вместо SMS необходимо позвонить на указанный номер и продиктовать код голосом. 0 — (по умолчанию) необходимо отправить SMS.
     *                      В случае если СМС не дошло до пользователя – необходимо вызвать метод повторно указав voice=1 и sid, полученный при первом вызове метода.
     *                      Флаг, может принимать значения 1 или 0
     * @param sex           Пол пользователя: 1 — женский, 2 — мужской.
     *                      Положительное число
     * @param sid           Идентификатор сессии, необходимый при повторном вызове метода, в случае если SMS сообщение доставлено не было. При первом вызове этот параметр не передается.
     * @return В случае успешного выполнения метода на номер телефона, указанный пользователем, будет отправлено SMS со специальным кодом, который может быть использован для завершения регистрации методом auth.confirm.
     * В качестве ответа будет возвращено поле sid, необходимое для повторного вызова метода в случае, если SMS-сообщение не дошло.
     * @throws VKAPIException 1000 Недопустимый номер телефона.
     * @throws VKAPIException 1004 Номер телефона занят другим пользователем.
     * @throws VKAPIException 1112 Обработка... Попробуйте позже.
     */
    public JSONObject signup(String first_name, String last_name, int client_id, String client_secret, String phone, String password, int test_mode, int voice, int sex, String sid) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponseWithoutAuthorization(name + "." + methodName, new String[][]{
                {"first_name", first_name},
                {"last_name", last_name},
                {"client_id", String.valueOf(client_id)},
                {"client_secret", client_secret},
                {"phone", phone},
                {"password", password},
                {"test_mode", String.valueOf(test_mode)},
                {"voice", String.valueOf(voice)},
                {"sex", String.valueOf(sex)},
                {"sid", String.valueOf(sid)}
        });
    }

    /**
     * Завершает регистрацию нового пользователя, начатую методом auth.signup, по коду, полученному через SMS.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param client_id     Идентификатор Вашего приложения.
     *                      ''''обязательный параметр''''
     * @param client_secret Секретный ключ приложения, доступный в резделе редактирования приложения.
     *                      ''''обязательный параметр''''
     * @param phone         Номер телефона регистрируемого пользователя. Номер телефона может быть проверен заранее методом auth.checkPhone.
     *                      ''''обязательный параметр''''
     * @param password      Пароль пользователя, который будет использоваться при входе. Не меньше 6 символов. Также пароль может быть указан позже, при вызове метода auth.confirm.
     * @param test_mode     1 — тестовый режим, при котором не будет зарегистрирован новый пользователь, но при этом номер не будет проверяться на использованность. 0 — (по умолчанию) рабочий.
     *                      Флаг, может принимать значения 1 или 0
     * @param intro         Битовая маска отвечающая за прохождение обучения использованию приложения.
     *                      Положительное число
     * @return В случае успешного завершения авторизации таким способом будет возвращён объект, содержащий поля success = 1 и uid = идентификатор зарегистрированного пользователя.
     * @throws VKAPIException 1110 Неправильный код.
     * @throws VKAPIException 1111 Недопустимый пароль.
     */
    public JSONObject confirm(int client_id, String client_secret, String phone, String code, String password, int test_mode, int intro) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponseWithoutAuthorization(name + "." + methodName, new String[][]{
                {"client_id", String.valueOf(client_id)},
                {"client_secret", client_secret},
                {"phone", phone},
                {"code", code},
                {"password", password},
                {"test_mode", String.valueOf(test_mode)},
                {"voice", String.valueOf(intro)},
        });
    }

    /**
     * Позволяет восстановить доступ к аккаунту, используя код, полученный через СМС.
     * Данный метод доступен только приложениям имеющим доступ к Прямой авторизации.
     * Это открытый метод, вы можете вызывать его не передавая access_token.
     *
     * @param phone Номер телефона пользователя.
     *              ''''обязательный параметр''''
     * @return В случае успеха метод возвращает объект содержащий следующие поля:<br />
     * success – 1<br />
     * sid – параметр необходимый для получения доступа по коду
     * <p/>
     * Для завершения восстановления доступа необходимо обратиться по адресу:<br />
     * https://oauth.vk.com/token?grant_type=restore_code&client_id={Идентификатор приложения}&client_secret={Секретный_ключ}&username={Номер телефона}&scope={Список прав доступа}&sid={Параметр, получаемый в данном методе}&code={Код полученный через СМС}
     * <p/>
     * Список параметров:<br />
     * grant_type – необходимо передать значение: restore_code<br />
     * client_id – Идентификатор приложения<br />
     * client_secret – Секретный ключ<br />
     * username – Номер телефона по которому был восстановлен пароль<br />
     * scope – список прав доступа, разделенных через запятую<br />
     * sid – идентификатор сессии, полученный в результате выполнения этого метода<br />
     * code – код, полученный через СМС<br />
     * <p/>
     * В результате авторизации через restore_code OAuth вернет данные аналогичные обычной авторизации, с дополнительным параметром change_password_hash необходимым для метода account.changePassword.
     * @throws VKAPIException 1105 Превшено ограничение на число попыток, попробуйте позже.
     */
    public JSONObject restore(String phone) throws VKAPIException {
        String methodName = Thread.currentThread().getStackTrace()[1].toString();
        methodName = methodName.substring(0, methodName.indexOf('('));
        methodName = methodName.substring(methodName.lastIndexOf('.') + 1);

        return getResponseWithoutAuthorization(name + "." + methodName, new String[][]{
                {"phone", phone},
        });
    }
}
