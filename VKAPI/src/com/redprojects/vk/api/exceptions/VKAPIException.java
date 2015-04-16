package com.redprojects.vk.api.exceptions;

import java.util.HashMap;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class VKAPIException extends Throwable {
    private static HashMap<Integer, VKAPIException> exceptions = new HashMap<>();

    private final int code;

    static {
        /*GlobalExceptions*/
        //var xzA=document.getElementById('xzT').getElementsByClassName('dev_param_row');var xzxz={};for(var i=0;i<xzA.length;i++)xzxz[xzA[i].getElementsByClassName('dev_error_code')[0].innerHTML]=xzA[i].getElementsByClassName('dev_error_code')[0].innerHTML+" "+xzA[i].getElementsByTagName('b')[0].innerHTML;console.log(JSON.stringify(xzxz));
        exceptions.put(1, new GlobalException("1 Произошла неизвестная ошибка."));
        exceptions.put(2, new GlobalException("2 Приложение выключено."));
        exceptions.put(3, new GlobalException("3 Передан неизвестный метод."));
        exceptions.put(4, new GlobalException("4 Неверная подпись."));
        exceptions.put(5, new GlobalException("5 Авторизация пользователя не удалась."));
        exceptions.put(6, new GlobalException("6 Слишком много запросов в секунду."));
        exceptions.put(7, new GlobalException("7 Нет прав для выполнения этого действия."));
        exceptions.put(8, new GlobalException("8 Неверный запрос."));
        exceptions.put(9, new GlobalException("9 Слишком много однотипных действий."));
        exceptions.put(10, new GlobalException("10 Произошла внутренняя ошибка сервера."));
        exceptions.put(11, new GlobalException("11 В тестовом режиме приложение должно быть выключено или пользователь должен быть залогинен."));
        exceptions.put(14, new GlobalException("14 Требуется ввод кода с картинки (Captcha). "));
        exceptions.put(15, new GlobalException("15 Доступ запрещён."));
        exceptions.put(16, new GlobalException("16 Требуется выполнение запросов по протоколу HTTPS, т.к. пользователь включил настройку, требующую работу через безопасное соединение."));
        exceptions.put(17, new GlobalException("17 Требуется валидация пользователя."));
        exceptions.put(20, new GlobalException("20 Данное действие запрещено для не Standalone приложений."));
        exceptions.put(21, new GlobalException("21 Данное действие разрешено только для Standalone и Open API приложений."));
        exceptions.put(23, new GlobalException("23 Метод был выключен."));
        exceptions.put(24, new GlobalException("24 Требуется подтверждение со стороны пользователя."));
        exceptions.put(100, new GlobalException("100 Один из необходимых параметров был не передан или неверен."));
        exceptions.put(101, new GlobalException("101 Неверный API ID приложения."));
        exceptions.put(113, new GlobalException("113 Неверный идентификатор пользователя."));
        exceptions.put(150, new GlobalException("150 Неверный timestamp"));
        exceptions.put(200, new GlobalException("200 Доступ к альбому запрещён."));
        exceptions.put(201, new GlobalException("201 Доступ к аудио запрещён."));
        exceptions.put(203, new GlobalException("203 Доступ к группе запрещён."));
        exceptions.put(300, new GlobalException("300 Альбом переполнен."));
        exceptions.put(500, new GlobalException("500 Действие запрещено. Вы должны включить переводы голосов в настройках приложения."));
        exceptions.put(600, new GlobalException("600 Нет прав на выполнение данных операций с рекламным кабинетом."));
        exceptions.put(603, new GlobalException("603 Произошла ошибка при работе с рекламным кабинетом."));
        /*AudioExceptions*/
        //message, getRecommendations
        exceptions.put(19, new AudioException("19 Контент недоступен."));
        //save
        exceptions.put(121, new AudioException("121 Неверный хэш."));
        exceptions.put(123, new AudioException("123 Недопустимый формат аудиозаписи."));
        exceptions.put(270, new AudioException("270 Аудиозапись была изъята по запросу правообладателя и не может быть загружена."));
        exceptions.put(301, new AudioException("301 Недопустимое имя файла."));
        exceptions.put(302, new AudioException("302 Недопустимый размер файла."));
        //addAlbum
        exceptions.put(302, new AudioException("302 Создано максимальное количество альбомов."));
    }

    public VKAPIException(String message) {
        super(message);
        this.code = -1;
    }

    public VKAPIException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @SuppressWarnings("FinalStaticMethod")
    public final static VKAPIException getException(int code) {
        if (exceptions.containsKey(code))
            return exceptions.get(code);

        return new VKAPIException("Exception not found", -1);
    }
}
